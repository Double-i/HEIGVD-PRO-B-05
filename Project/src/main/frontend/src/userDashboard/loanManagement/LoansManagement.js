import React, {useState, useContext, useEffect} from 'react'
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

const LOANS_REQUEST = "/loans/find/user/"
const LOANS_UPDATE_STATE_REQUEST = "/loans/"

// used to know which container should be update
let containerToUpdate;
let containerMethodtoUpdate;

function LoansManagement(props) {

    const session = useContext(SessionContext)
    const username = session.session.getUserName()
    const [pendingLoans, setPendingLoans] = useState([]);
    const [inComingLoans, setInComingLoans] = useState([]);
    const [onGoingLoans, setOnGoingLoans] = useState([]);
    const [passedLoans, setPassedLoans] = useState([]);
    const [refusedLoans, setRefusedLoans] = useState([]);
    const [showShortenLoansModal, setShowShortenLoansModal] = useState(false);
    const [showNewPeriodModal, setShowNewPeriodModal] = useState(false);

    const [loan, setLoan] = useState({validPeriod: moment(), periods: []}); // should be defined because of ShortenLoanModal
    const role = props.match.params.role

    // If the url param isn't correct we redirect the user on the home page
    if(role !== "borrower" && role !== "owner"){
        props.history.push("/home")
    }

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
            borrower:  isBorrower(role),
            state: STATE.accepted,
            startGT: moment().add("1", "days").format('YYYY-MM-DD')
        }).then((result) => {
            setInComingLoans(result);
        }, (error) => {
            console.log(error)
        })

        // Request for ongoing loans
        sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
            borrower:  isBorrower(role),
            state: STATE.accepted,
            startLT: moment().add(1,"days").format('YYYY-MM-DD'),
            endGT: moment().format('YYYY-MM-DD')

        }).then((result) => {
            setOnGoingLoans(result)
        }, (error) => {
            console.log(error)
        })

        // Request for passed loans
        sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
            borrower:  isBorrower(role),
            state: STATE.accepted,
            endLT: moment().format('YYYY-MM-DD')
        }).then((result) => {
            setPassedLoans(result)
        }, (error) => {
            console.log(error)
        })

        // Request for refused loans
        sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
            borrower:  isBorrower(role),
            state: STATE.refused,
        }).then((result) => {

            sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
                borrower:  isBorrower(role),
                state: STATE.pending,
                startLT: moment().add(1,"days").format('YYYY-MM-DD')
            }).then((resultPassedPending) => {
                setRefusedLoans(result.concat(resultPassedPending))

            }, (error) => {
                console.log(error)
            })
        }, (error) => {
            console.log(error)
        })


    }, []);

    const btnDisplayToolClicked = (loan) => {
        props.history.push(`/tooldetails/${loan.ezobject.id}`)
    }
    const btnCancelLoanClicked = (loan) => {

        sendEzApiRequest(LOANS_UPDATE_STATE_REQUEST + `${loan.pkLoan}/state`, 'PATCH', {
            state: STATE.cancel
        }).then((result) => {
            const newPendingLoans = [...pendingLoans]; // make a separate copy of the array
            const index = newPendingLoans.indexOf(loan)
            if (index !== -1) {
                newPendingLoans.splice(index, 1);
                setPendingLoans(newPendingLoans)
            }
        }, (error) => {
            console.log(error)
        })
    }

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

        return (startDateLLoan.isBetween(startDateRLoan, endDateRLoan,  null, '[]')
            || endDateLLoan.isBetween(startDateRLoan, endDateRLoan, null, '[]')
            || startDateRLoan.isBetween( startDateLLoan, endDateRLoan, null, '[]'))
    }

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

            for(let i = 0 ; i < pendingLoans.length; ++i){
                if(pendingLoans[i].ezobject.id === loan.ezobject.id && pendingLoans[i].pkLoan !== loan.pkLoan){
                    if(overlapPeriod(pendingLoans[i], loan)){
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


    const btnShowPeriodClicked = (loan) => {
        console.log("wtf")
        // Show modal
        setLoan(loan)
        setShowShortenLoansModal(true)
    };

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

    const btnPassBackClicked = (loan) => {
        setLoan(loan)
        setShowNewPeriodModal(true)
    };


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

    const refusePeriod = (loan, period) => {
        updatePeriodState(loan, period, STATE.refused)
    };

    const acceptPeriod = (loan, period) => {
        updatePeriodState(loan, period, STATE.accepted)
        const newContainerToUpdate = [...containerToUpdate]
        const idxLoan = newContainerToUpdate.indexOf(loan)
        if(idxLoan !== -1){
            newContainerToUpdate[idxLoan].validPeriod = period
            containerMethodtoUpdate(newContainerToUpdate)
        }

    };

    const cancelPeriod = (loan, period) => {
        updatePeriodState(loan, period, STATE.cancel)
    };
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

        }, error => {
            console.log(error)
        })
    };
    const getPageTitle = () => {
        if(role === "borrower"){
            return "Vos demande d'emprunts (coté emprunteur)"
        }else {
            return "Les demandes d'emprunts sur vos outils (coté propriétaire)"
        }

    }

    return (
        <Container>
            <h2>{getPageTitle()}</h2>
            <NewPeriodModal show={showNewPeriodModal}
                            onHide={() => setShowNewPeriodModal(false)}
                            addPeriod={addPeriod}
                            loan={loan}
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
                loansData={pendingLoans.map((value, number) => value)}
                isOwner={isOwner(role)}
                actionButtons={(isBorrower(role))?[
                    {
                        action: btnDisplayToolClicked,
                        label: 'Afficher l\'outil',
                    },
                    {
                        action: btnCancelLoanClicked,
                        label: 'Annuler',
                    },
                ]: [
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
                        label: 'Voir retour',
                    },
                    {
                        action: (loan, newEndDate) => {
                            containerToUpdate = inComingLoans
                            containerMethodtoUpdate = setInComingLoans
                            btnPassBackClicked(loan)
                        },
                        label: 'Demande retour'
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
                        label: 'Voir retour',
                    },
                    {
                        action: (loan) => {
                            containerToUpdate = onGoingLoans
                            containerMethodtoUpdate = setOnGoingLoans
                            btnPassBackClicked(loan)
                        },
                        label: 'Demande retour',
                    }
                ]}
            />
            <hr/>
            <h3>Emprunts passés </h3>
            <LoansList
                loansData={passedLoans}
                isOwner={isOwner(role)}
                actionButtons={[
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
