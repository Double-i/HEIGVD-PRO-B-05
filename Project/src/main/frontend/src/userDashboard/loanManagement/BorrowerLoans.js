import React, { useState, useContext, useEffect } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import {  Container } from 'react-bootstrap'
import { withRouter } from 'react-router-dom'
import LoansList from './LoansList'
import {sendEzApiRequest} from "../../common/ApiHelper";
import * as moment from 'moment'
import {SessionContext} from '../../common/SessionHelper'
import {STATE} from "../../common/State";

const LOANS_REQUEST = "/loans/find/user/"
const LOANS_CANCEL_REQUEST = "/loans/"

function BorrowerLoans(props) {

    const session = useContext(SessionContext)
    const username = session.session.getUserLastName()
    const [pendingLoans, setPendingLoans] = useState([]);
    const [inComingLoans, setInComingLoans] = useState([]);
    const [onGoingLoans, setOnGoingLoans] = useState([]);
    const [passedLoans, setPassedLoans] = useState([]);
    const [refusedLoans, setRefusedLoans] = useState([]);

    console.log(moment().format('YYYY-MM-DD'))

    useEffect(() => {
        console.log("test",username)

        // Request for pending loans
        sendEzApiRequest(LOANS_REQUEST+username,'GET',{}, {
            borrower : true,
            state: STATE.pending,
            startGT: moment().format('YYYY-MM-DD')
        }).then((result)=> {
            setPendingLoans(result);
        },(error) => {
            console.log(error)
        })

        // Request for incoming loans
        sendEzApiRequest(LOANS_REQUEST+username, 'GET',{},{
            borrower: true,
            state: STATE.accepted,
            startGT: moment().format('YYYY-MM-DD')
        }).then((result)=>{
            setInComingLoans(result);
        }, (error)=> {
            console.log(error)
        })

        // Request for ongoing loans
        sendEzApiRequest(LOANS_REQUEST+username, 'GET',{},{
            borrower: true,
            state: STATE.accepted,
            startLT: moment().format('YYYY-MM-DD'),
            endGT: moment().format('YYYY-MM-DD')
        }).then((result)=>{
            setOnGoingLoans(result)
        }, (error)=> {
            console.log(error)
        })

        // TODO possiblement 2 requêtes: 1 pour les réfuser, une pour les passer
        // Request for refused and passed loans
        sendEzApiRequest(LOANS_REQUEST+username, 'GET',{},{
            borrower: true,
            state: STATE.accepted,
            endLT: moment().format('YYYY-MM-DD')
        }).then((result)=>{

            setPassedLoans(result)
        }, (error)=> {
            console.log(error)
        })

        sendEzApiRequest(LOANS_REQUEST+username, 'GET',{},{
            borrower: true,
            state: STATE.refused,
        }).then((result)=>{
            setRefusedLoans(result)
        }, (error)=> {
            console.log(error)
        })


    },[]); // the array is to avoid infinite loop https://stackoverflow.com/a/54923969

    const btnDisplayToolClicked = (toolId) => {
        props.history.push(`/tools/${toolId}`)
    }
    const btnShortenLoanClicked = (loanId) => {
        console.log("Raccourcir la durée de l'emprunt id : ", loanId)
    }
    const btnCancelLoanClicked = (loan) => {

       sendEzApiRequest(LOANS_CANCEL_REQUEST+`${loan.pkLoan}/state`, 'PATCH', {
            state: STATE.cancel
        }).then((result) => {
           const newPendingLoans = [...pendingLoans]; // make a separate copy of the array
           const index = newPendingLoans.indexOf(loan)
           if (index !== -1) {
               newPendingLoans.splice(index, 1);
               setPendingLoans(newPendingLoans)
           }
        }, (error) =>{
            console.log(error)
        })
    }
    return (
        <Container>
            <h3>Demande d'emprunt en attente</h3>
            <LoansList
                loansData={pendingLoans.map((value,number) => value)}
                isOwner={false}
                actionButtons={[
                    {
                        action: btnDisplayToolClicked,
                        label: 'Afficher l\'outil',
                    },
                    {
                        action: btnCancelLoanClicked,
                        label: 'Annuler',
                    },
                ]}
            />
            <h3>Emprunt à venir</h3>
            <LoansList
                loansData={inComingLoans}
                isOwner={false}
                actionButtons={[
                    {
                        action: btnDisplayToolClicked,
                        label: 'Afficher l\'outil',
                    },
                    {
                        action: btnShortenLoanClicked,
                        label: 'Raccourcir',
                    }
                ]}
            />
            <hr />
            <h3>Emprunt en cours</h3>
            <LoansList
                loansData={onGoingLoans}
                actionButtons={[
                    {
                        action: (loanId) => {
                            console.log('Afficher le loan id: ', loanId)
                        },
                        label: 'Afficher',
                    },
                    {
                        action: (loanId) => {
                            console.log('Raccourcir le loan id: ', loanId)
                        },
                        label: 'Raccourcir',
                    },
                ]}
            />
            <hr />
            <h3>Emprunts passés </h3>
            <LoansList
                loansData={passedLoans}
                actionButtons={[
                    {
                        action: (loanId) => {
                            console.log('Afficher le loan id: ', loanId)
                        },
                        label: 'Afficher l\'outil',
                    },
                ]}
            />
            <h3>Demandes refusées </h3>
            <LoansList
                loansData={refusedLoans}
                actionButtons={[
                    {
                        action: (loanId) => {
                            console.log('Afficher le loan id: ', loanId)
                        },
                        label: 'Afficher l\'outil',
                    },
                ]}
            />
        </Container>
    )
}
export default withRouter(BorrowerLoans)
