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

const LOANS_REQUEST = "/loans/find/user/"
const LOANS_UPDATE_STATE_REQUEST = "/loans/"


function OwnerLoans(props) {
    const session = useContext(SessionContext);
    const username = session.session.getUserLastName();
    const [pendingLoans, setPendingLoans] = useState([]);
    const [inComingLoans, setInComingLoans] = useState([]);
    const [onGoingLoans, setOnGoingLoans] = useState([]);
    const [passedLoans, setPassedLoans] = useState([]);
    const [refusedLoans, setRefusedLoans] = useState([]);
    const [showShortenLoansModal, setShowShortenLoansModal] = useState(false);
    const [loan, setLoan] = useState({periods: []}) // should be defined because of ShortenLoanModal


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

        // TODO possiblement 2 requêtes: 1 pour les réfuser, une pour les passer
        // Request for refused and passed loans
        sendEzApiRequest(LOANS_REQUEST + username, 'GET', {}, {
            borrower: false,
            state: "accepted",
            endLT: moment().format('YYYY-MM-DD')
        }).then((result) => {

            setPassedLoans(result)
        }, (error) => {
            console.log(error)
        });

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
        console.log("wtf")
        // Show modal
        setLoan(loan)
        setShowShortenLoansModal(true)
        console.log(showShortenLoansModal)
    };

    const btnPassBackClicked = (loan) => {
        console.log("ask pass back")
    };
    const updatePeriodState = (loan, period, state) => {
        console.log("UPDATE" ,loan, period, state)
        return sendEzApiRequest(LOANS_UPDATE_STATE_REQUEST+ `${loan.pkLoan}/periods/${period.id}/state`, 'PATCH', {
            state: state
        })
    };
    const refusePeriod = (loan, period) => {
        updatePeriodState(loan, period, STATE.refused).then( result => {
            console.log(result)
        }, (error)=>{
            console.log(error)
        })
    };
    const acceptPeriod = (loan, period) => {
        updatePeriodState(loan, period, STATE.accepted).then( result =>{
            console.log(result)
        },error => {
            console.log(error)
        })
    };

    const cancelPeriod = (loan, period) => {
        updatePeriodState(loan, period, STATE.cancel).then( result =>{
            console.log(result)
        },error => {
            console.log(error)
        })
    };

    return (

        <Container>
            <ShortenLoanModal show={showShortenLoansModal}
                              onHide={() => setShowShortenLoansModal(false)}
                              loan={loan}
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
                        action: btnShortenLoanClicked,
                        label: 'Raccourcir',
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
                        action: (loanId) => {
                            console.log('Afficher le loan id: ', loanId)
                        },
                        label: 'Afficher',
                    },
                    {
                        action: btnShowPeriodClicked,
                        label: 'Voir retour',
                    },
                    {
                        action:  btnPassBackClicked,
                        label: 'Demande retour',
                    },

                ]}
            />
            <hr/>
            <h3>Emprunts passés </h3>
            <LoansList
                loansData={passedLoans}
                isOwner={true}
                actionButtons={[
                    {
                        action: (loanId) => {
                            console.log('Afficher le loan id: ', loanId)
                        },
                        label: 'Afficher',
                    },
                ]}
            />
            <h3>Demandes refusées </h3>
            <LoansList
                loansData={refusedLoans}
                isOwner={true}
                actionButtons={[
                    {
                        action: (loanId) => {
                            console.log('Afficher le loan id: ', loanId)
                        },
                        label: 'Afficher',
                    },
                ]}
            />
        </Container>
    )
}

export default withRouter(OwnerLoans)
