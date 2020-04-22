import React, {useContext, useEffect, useState} from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import {Container, Modal} from 'react-bootstrap'
import LoansList from './LoansList'
import {SessionContext} from "../../common/SessionHelper";
import * as moment from "moment";
import {sendEzApiRequest} from "../../common/ApiHelper";
import {STATE} from "../../common/State";
import ShortenLoanModal from "./ShortenLoanModal";
import {withRouter} from "react-router-dom";
import NewPeriodModal from "./NewPeriodModal";
import {ROLE} from "../../common/Role";

const LOANS_REQUEST = "/loans/find/user/"
const LOANS_UPDATE_STATE_REQUEST = "/loans/"

// used to know which container should be update
let containerToUpdate;
let containerMethodtoUpdate;

function OwnerLoans(props) {
    const session = useContext(SessionContext);
    const username = session.session.getUserName();
    const [pendingLoans, setPendingLoans] = useState([]);
    const [inComingLoans, setInComingLoans] = useState([]);
    const [onGoingLoans, setOnGoingLoans] = useState([]);
    const [passedLoans, setPassedLoans] = useState([]);
    const [refusedLoans, setRefusedLoans] = useState([]);

    const [showShortenLoansModal, setShowShortenLoansModal] = useState(false);
    const [showNewPeriodModal, setShowNewPeriodModal] = useState(false)

    const [loan, setLoan] = useState({validPeriod: moment(), periods: []}) // should be defined because of ShortenLoanModal

    // Get loans data
    useEffect(() => {
        // Request for pending loans
        sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
            borrower: false,
            state: "pending",
            startGT: moment().format('YYYY-MM-DD')
        }).then((result) => {
            setPendingLoans(result);
        }, (error) => {
            console.log(error)
        });

        // Request for incoming loans
        sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
            borrower: false,
            state: "accepted",
            startGT: moment().format('YYYY-MM-DD')
        }).then((result) => {
            setInComingLoans(result);
        }, (error) => {
            console.log(error)
        });

        // Request for ongoing loans
        sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
            borrower: false,
            state: "accepted",
            startLT: moment().format('YYYY-MM-DD'),
            endGT: moment().format('YYYY-MM-DD')
        }).then((result) => {
            setOnGoingLoans(result)
        }, (error) => {
            console.log(error)
        });

        // Request for passed loans
        sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
            borrower: false,
            state: "accepted",
            endLT: moment().format('YYYY-MM-DD')
        }).then((result) => {

            setPassedLoans(result)
        }, (error) => {
            console.log(error)
        });

        // Request for refused loans
        sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
            borrower: false,
            state: "refused",
        }).then((result) => {
            setRefusedLoans(result)
        }, (error) => {
            console.log(error)
        })
    }, []); // the array is to avoid infinite loop https://stackoverflow.com/a/54923969

    const btnDisplayToolClicked = (loan) => {
        console.log("DISPLAY TOOL", loan)
        props.history.push(`/tools/${loan.ezobject.id}`)
    }

    const btnShortenLoanClicked = (loan) => {
        console.log("Raccourcir la durée de l'emprunt id : ", loan)
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

    const btnAcceptedClicked = (loan) => {
        updateLoanState(loan, STATE.accepted).then((result) => {
            const newInComingLoans = [...inComingLoans]
            loan.state = STATE.accepted
            newInComingLoans.push(loan)
            setInComingLoans(newInComingLoans)

        }, (error) => {
            console.log(error)
        })
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

    const btnShowPeriodClicked = (loan) => {
        // Show modal
        setLoan(loan)
        setShowShortenLoansModal(true)

    };

    const btnPassBackClicked = (loan) => {
        setLoan(loan)
        setShowNewPeriodModal(true)
    };

    const updatePeriodState = (loan, period, state) => {
        console.log("UPDATE", loan, period, state)
        return sendEzApiRequest(LOANS_UPDATE_STATE_REQUEST + `${loan.pkLoan}/periods/${period.id}/state`, 'PATCH', {
            state: state
        }).then(result => {
            const newContainerToUpdate = [...containerToUpdate]
            const idxLoan = newContainerToUpdate.indexOf(loan)
            const idxPeriod = newContainerToUpdate[idxLoan].periods.indexOf(period)
            const newPeriod = newContainerToUpdate[idxLoan].periods[idxPeriod]
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
            newContainerToUpdate[idxLoan].periods.push({
                id: result.id,
                dateStart: loan.validPeriod.dateStart,
                dateEnd: newEndDate,
                creator: "owner",
                state: STATE.pending
            })
            console.log("PROJET: ", newContainerToUpdate)
            containerMethodtoUpdate(newContainerToUpdate)

        }, error => {
            console.log(error)
        })
    };

    return (
        <Container>
            <NewPeriodModal show={showNewPeriodModal}
                            onHide={() => setShowNewPeriodModal(false)}
                            addPeriod={addPeriod}
                            loan={loan}
            />
            <ShortenLoanModal show={showShortenLoansModal}
                              onHide={() => setShowShortenLoansModal(false)}
                              loan={loan}
                              role={ROLE.owner}
                              refusePeriod={refusePeriod}
                              acceptPeriod={acceptPeriod}
                              cancelPeriod={cancelPeriod}

            />

            <h3>Demande d'emprunt en attente</h3>
            <LoansList
                loansData={pendingLoans}
                isOwner={true}
                actionButtons={[
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
                isOwner={true}
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
                isOwner={true}
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
                isOwner={true}
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
                isOwner={true}
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

export default withRouter(OwnerLoans)