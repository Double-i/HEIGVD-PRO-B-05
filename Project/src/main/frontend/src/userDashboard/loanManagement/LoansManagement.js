import React, {useContext, useEffect, useState} from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import {Container} from 'react-bootstrap'
import {withRouter} from 'react-router-dom'
import LoansList from './LoansList'
import {sendEzApiRequest} from "../../common/ApiHelper";
import * as moment from 'moment'
import {SessionContext} from '../../common/SessionHelper'
import {STATE} from "../../common/State";
import NewPeriodModal from "./NewPeriodModal";
import ShortenLoanModal from "./ShortenLoanModal";
import {isBorrower, isOwner, ROLE} from "../../common/Role";
import {formatString} from "../../common/Utils";

const LOANS_REQUEST = "/loans/find/user/"
const LOANS_UPDATE_STATE_REQUEST = "/loans/"


// used to know which container should be update
let containerToUpdate;
let containerMethodtoUpdate;

/**
 * LoansManagement is a component which display different list of LoansList which represent the loans of the user in
 * different state (pending loans, confirmed loans etc.)
 * @param props
 * @returns {React.Component}
 * @constructor
 */
function LoansManagement(props) {

    const session = useContext(SessionContext)
    const username = session.session.getUserName()

    // These states are used to update mananage the different type of loan (passed, pending, incomming etc.)
    const [pendingLoans, setPendingLoans] = useState([]);
    const [inComingLoans, setInComingLoans] = useState([]);
    const [onGoingLoans, setOnGoingLoans] = useState([]);
    const [passedLoans, setPassedLoans] = useState([]);
    const [refusedLoans, setRefusedLoans] = useState([]);

    // These states are used to show modal for ask a shorten or a new period
    const [showShortenLoansModal, setShowShortenLoansModal] = useState(false);
    const [showNewPeriodModal, setShowNewPeriodModal] = useState(false);
    const [receiveMessageResponse, setReceiveMessageResponse] = useState(false)
    const [newPeriodResponse, setNewPeriodResponse] = useState({message: ""})


    const [loan, setLoan] = useState({validPeriod: moment(), periods: []}); // should be defined because of ShortenLoanModal

    // Determine the user role
    const role = props.borrower ? ROLE.borrower : ROLE.owner

    // If the url param isn't correct we redirect the user on the home page
    if (role !== ROLE.borrower && role !== ROLE.owner) {
        props.history.push("/home")

    }

    // Request to send to get the loans
    useEffect(() => {
        // Request for pending loans
        sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
            borrower: isBorrower(role),
            state: STATE.pending,
            startGT: moment().add("1", "days").format('YYYY-MM-DD')
        }).then((result) => {
            setPendingLoans(result);
        }, (error) => {
            console.log(error)
        })

        // Request for incoming loans
        sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
            borrower: isBorrower(role),
            state: STATE.accepted,
            startGT: moment().add("1", "days").format('YYYY-MM-DD')
        }).then((result) => {
            setInComingLoans(result);
        }, (error) => {
            console.log(error)
        })

        // Request for ongoing loans
        sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
            borrower: isBorrower(role),
            state: STATE.accepted,
            startLT: moment().add(1, "days").format('YYYY-MM-DD'),
            endGT: moment().format('YYYY-MM-DD')

        }).then((result) => {
            setOnGoingLoans(result)
        }, (error) => {
            console.log(error)
        })

        // Request for passed loans
        sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
            borrower: isBorrower(role),
            state: STATE.accepted,
            endLT: moment().format('YYYY-MM-DD')
        }).then((result) => {
            setPassedLoans(result)
        }, (error) => {
            console.log(error)
        })

        // Request for refused loans
        sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
            borrower: isBorrower(role),
            state: formatString("{0},{1}", STATE.refused, STATE.cancel),
        }).then((result) => {
            sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
                borrower: isBorrower(role),
                state: STATE.pending,
                startLT: moment().add(1, "days").format('YYYY-MM-DD')
            }).then((resultPassedPending) => {
                setRefusedLoans(result.concat(resultPassedPending))

            }, (error) => {
                console.log(error)
            })
        }, (error) => {
            console.log(error)
        })


    }, [props.borrower]);

    /**
     * Redirect the user on the object details page
     *
     * @param loan
     */
    const btnDisplayToolClicked = (loan) => {
        props.history.push(`/tooldetails/${loan.ezobject.id}`)
    }
    /**
     * Triggered when the cancel button is clicked. It's cancel the loan, remove it from the current container
     * (pending or incoming loans container) and add it to the refusedLoans container
     * @param loan
     */
    const btnCancelLoanClicked = (loan) => {

        sendEzApiRequest(LOANS_UPDATE_STATE_REQUEST + `${loan.pkLoan}/state`, 'PATCH', {
            state: STATE.cancel
        }).then((result) => {
            const newPendingLoans = [...containerToUpdate]; // make a separate copy of the array
            const index = newPendingLoans.indexOf(loan)
            if (index !== -1) {

                // Add loan to refused array
                const loan = newPendingLoans[index]
                loan.state = STATE.cancel;
                const newRefusedLoans = [...refusedLoans]
                newRefusedLoans.push(loan)
                setRefusedLoans(newRefusedLoans)

                //Remove loans from previous container
                newPendingLoans.splice(index, 1);
                containerMethodtoUpdate(newPendingLoans)

            }
        }, (error) => {
            console.log(error)
        })
    }

    /**
     * Update the loan state use to cancel, accepted or refused a loan
     * @param loan
     * @param state
     * @returns {Promise<unknown>}
     */
    const updateLoanState = (loan, state) => {

        return sendEzApiRequest(LOANS_UPDATE_STATE_REQUEST + `${loan.pkLoan}/state`, 'PATCH', {
            state: state
        }).then((result) => {
            const newPendingLoans = [...pendingLoans]; // make a separate copy of the array
            const index = newPendingLoans.indexOf(loan)
            if (index !== -1) {
                newPendingLoans.splice(index, 1)
                setPendingLoans(newPendingLoans)
            }
        }, (error) => {
            console.log(error)
        })
    };

    /**
     * Return a true if the rLoan and rLoan periods overlapped
     *
     * @param lLoan
     * @param rLoan
     */
    const overlapPeriod = (lLoan, rLoan) => {
        const startDateLLoan = moment(lLoan.validPeriod.dateStart)
        const endDateLLoan = moment(lLoan.validPeriod.dateEnd)

        const startDateRLoan = moment(rLoan.validPeriod.dateStart)
        const endDateRLoan = moment(rLoan.validPeriod.dateEnd)

        return (startDateLLoan.isBetween(startDateRLoan, endDateRLoan, null, '[]')
            || endDateLLoan.isBetween(startDateRLoan, endDateRLoan, null, '[]')
            || startDateRLoan.isBetween(startDateLLoan, endDateRLoan, null, '[]'))
    }

    /**
     * Triggered when the owner accepts a loan. It changes the state of the loan and move it to the corresponding loans container
     * NOTE: if a loan with the same object and an overlapping period it refused it.
     *
     * @param loan
     */
    const btnAcceptedClicked = (loan) => {
        updateLoanState(loan, STATE.accepted).then((result) => {
            const newInComingLoans = [...inComingLoans]
            loan.state = STATE.accepted
            newInComingLoans.push(loan)
            setInComingLoans(newInComingLoans)

            const newPendingLoans = [...pendingLoans]; // make a separate copy of the array
            const newRefusedLoans = [...refusedLoans];

            const index = newPendingLoans.indexOf(loan)
            if (index !== -1) {
                newPendingLoans.splice(index, 1)
            }

            for (let i = 0; i < pendingLoans.length; ++i) {
                if (pendingLoans[i].ezobject.id === loan.ezobject.id && pendingLoans[i].pkLoan !== loan.pkLoan) {
                    if (overlapPeriod(pendingLoans[i], loan)) {
                        const index = newPendingLoans.indexOf(pendingLoans[i])
                        if (index !== -1) {
                            pendingLoans[i].state = STATE.refused
                            newRefusedLoans.push(pendingLoans[i])
                            newPendingLoans.splice(index, 1)
                        }
                    }
                }
            }
            setPendingLoans(newPendingLoans)
            setRefusedLoans(newRefusedLoans)

        }, (error) => {
            console.log(error)
        })
    };

    /**
     * Display periods loan.
     *
     * @param loan
     */
    const btnShowPeriodClicked = (loan) => {
        // Show modal
        setLoan(loan)
        setShowShortenLoansModal(true)
    };

    /**
     * Refused the loan. Similar to accept method.
     *
     * @param loan
     */
    const btnRefusedClicked = (loan) => {
        updateLoanState(loan, STATE.refused).then((result) => {
            const newRefusedLoans = [...refusedLoans]
            loan.state = STATE.refused
            newRefusedLoans.push(loan)
            setRefusedLoans(newRefusedLoans)
        }, (error) => {
            console.log(error)
        })
    };
    /**
     * This function is triggered when the owner click on asks for tool back when the loans has been accepted
     * but the period is passed
     *
     * @param loan
     */
    const btnPassBackClicked = (loan) => {
        setLoan(loan)
        setShowNewPeriodModal(true)
    };

    /**
     * Update the period state
     *
     * @param loan to update
     * @param period to update
     * @param state the new state
     * @returns {Promise<>} the request send to eztool API
     */
    const updatePeriodState = (loan, period, state) => {

        return sendEzApiRequest(LOANS_UPDATE_STATE_REQUEST + `${loan.pkLoan}/periods/${period.id}/state`, 'PATCH', {
            state: state
        }).then(result => {
            const newContainerToUpdate = [...containerToUpdate];
            period.state = state
            containerMethodtoUpdate(newContainerToUpdate)
        }, error => {
            console.log(error)
        })
    };

    /**
     * This function is triggered when a user refuses shorten request of a user
     * @param loan the loan that owns the period
     * @param period id of the period
     */
    const refusePeriod = (loan, period) => {
        updatePeriodState(loan, period, STATE.refused)
    };

    /**
     * This function is triggered when a user accepts shorten request of a user
     * @param loan the loan that owns the period
     * @param period id of the period
     */
    const acceptPeriod = (loan, period) => {
        updatePeriodState(loan, period, STATE.accepted)
        const newContainerToUpdate = [...containerToUpdate]
        const idxLoan = newContainerToUpdate.indexOf(loan)
        if (idxLoan !== -1) {
            newContainerToUpdate[idxLoan].validPeriod = period
            containerMethodtoUpdate(newContainerToUpdate)
        }

    };

    /**
     * Use to cancel a period.
     *
     * @param loan the loan that own the period
     * @param period the period to cancel
     */
    const cancelPeriod = (loan, period) => {
        updatePeriodState(loan, period, STATE.cancel).then(result => console.log(result), error => console.log(error))
    };

    /**
     * Function triggered when the user want to add a period shorten request
     *
     * @param loan the loan which owns the new period
     * @param newEndDate the new end period
     */
    const addPeriod = (loan, newEndDate) => {
        sendEzApiRequest(LOANS_UPDATE_STATE_REQUEST + `${loan.pkLoan}/periods/`, 'POST', {
            dateStart: loan.validPeriod.dateStart,
            dateEnd: newEndDate
        }).then(result => {
            const newContainerToUpdate = [...containerToUpdate]
            const idxLoan = newContainerToUpdate.indexOf(loan)

            // filter the periods to get only the ones created by the borrower
            // then update their state to cancel.
            newContainerToUpdate[idxLoan].periods.filter((period, idx) => period.creator === ROLE.borrower && period.state === STATE.pending)
                .forEach((period, idx) => {
                    period.state = STATE.cancel
                })

            newContainerToUpdate[idxLoan].periods.push({
                id: result.id,
                dateStart: loan.validPeriod.dateStart,
                dateEnd: newEndDate,
                creator: role,
                state: STATE.pending
            })

            containerMethodtoUpdate(newContainerToUpdate)
            setNewPeriodResponse({error: false, message: "Votre demande a bien été effectuée"})

        }, error => {
            setNewPeriodResponse({
                error: true,
                message: "Votre demande n'est pas valide. Assurez-vous d'avoir bien fourni une date"
            })
            console.log(error)
        })
    };
    /**
     * Simple utility function to display different title on the page if the user is owner or borrower
     * @returns {string}
     */
    const getPageTitle = () => {
        if (role === "borrower") {
            return "Vos demande d'emprunts (coté emprunteur)"
        } else {
            return "Les demandes d'emprunts sur vos outils (coté propriétaire)"
        }

    }

    /**
     * This function is triggered when the owner click on the button "demande retour" of accepted loan which the endDate
     * has been passed. (In other words, the loan is finished but the borrower hasn't gave back the tool)
     * @param loan
     */
    const askForComeBack = (loan)=> {
        sendEzApiRequest(formatString("{0}{1}/askback", LOANS_UPDATE_STATE_REQUEST,loan.pkLoan),'GET')
            .then(result => {
            console.log(result)

        },error => {
            console.log(error)
        })
    }

    return (
        <Container>
            <h2>{getPageTitle()}</h2>
            <NewPeriodModal show={showNewPeriodModal}
                            onHide={() => {
                                setShowNewPeriodModal(false)
                                setReceiveMessageResponse(false)
                                setNewPeriodResponse({message: ""})
                            }}
                            addPeriod={addPeriod}
                            loan={loan}
                            response={newPeriodResponse}
            />

            <ShortenLoanModal show={showShortenLoansModal}
                              onHide={() => setShowShortenLoansModal(false)}
                              loan={loan}
                              role={role}
                              refusePeriod={refusePeriod}
                              acceptPeriod={acceptPeriod}
                              cancelPeriod={cancelPeriod}
            />
            <h3>Demande d'emprunt en attente</h3>
            <LoansList
                loansData={pendingLoans.map((value) => value)}
                isOwner={isOwner(role)}
                actionButtons={(isBorrower(role)) ? [
                    {
                        action: btnDisplayToolClicked,
                        label: 'Afficher l\'outil',
                    },
                    {
                        action: (loan) => {
                            containerToUpdate = pendingLoans
                            containerMethodtoUpdate = setPendingLoans
                            btnCancelLoanClicked(loan)
                        },
                        label: 'Annuler',
                    },
                ] : [
                    {
                        action: btnDisplayToolClicked,
                        label: 'Afficher',
                    },
                    {
                        action: btnAcceptedClicked,
                        label: 'Accepter',
                    },
                    {
                        action: btnRefusedClicked,
                        label: 'Refuser',
                    }
                ]}
            />
            <h3>Emprunt à venir</h3>
            <LoansList
                loansData={inComingLoans}
                isOwner={isOwner(role)}
                actionButtons={[
                    {
                        action: btnDisplayToolClicked,
                        label: 'Afficher',
                    },
                    {
                        action: (loan) => {
                            containerToUpdate = inComingLoans
                            containerMethodtoUpdate = setInComingLoans
                            btnShowPeriodClicked(loan)
                        },
                        label: 'Racourcissement',
                    },
                    {
                        action: (loan) => {
                            containerToUpdate = inComingLoans
                            containerMethodtoUpdate = setInComingLoans
                            btnPassBackClicked(loan)
                        },
                        label: 'Demande raccourcissement'
                    },
                    {
                        action: (loan) => {
                            containerToUpdate = inComingLoans
                            containerMethodtoUpdate = setInComingLoans
                            btnCancelLoanClicked(loan)
                        },
                        label: "Annuler"
                    }
                ]}
            />
            <hr/>
            <h3>Emprunt en cours</h3>
            <LoansList
                loansData={onGoingLoans}
                isOwner={isOwner(role)}
                actionButtons={[
                    {
                        action: btnDisplayToolClicked,
                        label: 'Afficher',
                    },
                    {
                        action: (loan) => {
                            containerToUpdate = onGoingLoans
                            containerMethodtoUpdate = setOnGoingLoans
                            btnShowPeriodClicked(loan)
                        },
                        label: 'Voir raccourcissement',
                    },
                    {
                        action: (loan) => {
                            containerToUpdate = onGoingLoans
                            containerMethodtoUpdate = setOnGoingLoans
                            btnPassBackClicked(loan)
                        },
                        label: 'Demande raccourcissement',
                    }
                ]}
            />
            <hr/>
            <h3>Emprunts passés </h3>
            <LoansList
                loansData={passedLoans}
                isOwner={isOwner(role)}
                actionButtons={(isOwner(role)) ? [{
                    action: btnDisplayToolClicked,
                    label: 'Afficher',
                }, {
                    action: askForComeBack,
                    label: 'Demande retour'
                }] : [
                    {
                        action: btnDisplayToolClicked,
                        label: 'Afficher',
                    }
                ]}
            />
            <h3>Demandes refusées </h3>
            <LoansList
                loansData={refusedLoans}
                isOwner={isOwner(role)}
                actionButtons={[
                    {
                        action: btnDisplayToolClicked,
                        label: 'Afficher',
                    }
                ]}
            />
        </Container>
    )
}

export default withRouter(LoansManagement)
