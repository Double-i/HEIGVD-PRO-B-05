import React, { useState, useContext, useEffect } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import {  Container } from 'react-bootstrap'
import { withRouter } from 'react-router-dom'
import LoansList from './LoansList'
import {sendEzApiRequest} from "../../common/ApiHelper";
import * as moment from 'moment'
import {SessionContext} from '../../common/SessionHelper'

const PENDING_REQUEST = "/loans/find/user/"

function BorrowerLoans(props) {
    const session = useContext(SessionContext)
    const username = session.session.getUserLastName()
    const [loans, setLoans] = useState([]);
    console.log(moment().format('YYYY-MM-DD'))

    useEffect(() => {
        sendEzApiRequest(PENDING_REQUEST+username,'GET',{}, {
            borrower : true,
            state: "pending",
            startGT: moment().format('YYYY-MM-DD')
        }).then((result)=> {
            console.log(result)
            setLoans(result);
        },(error) => {
            console.log(error)
        })
    },[]); // the array is to avoid infinite loop https://stackoverflow.com/a/54923969

    const btnDisplayLoanClicked = (toolId) => {
        props.history.push(`/tools/${toolId}`)
    }
    const btnShortenLoanClicked = (loanId) => {
        console.log("Raccourcir la durée de l'emprunt id : ", loanId)
    }
    const btnCancelLoanClicked = (loanId) => {
        console.log('Annuler la réservation id : ', loanId)
    }
    return (
        <Container>
            <h3>Demande d'emprunt en attente</h3>
            <LoansList
                loansData={loans}
                isOwner={false}
                actionButtons={[
                    {
                        action: btnDisplayLoanClicked,
                        label: 'Afficher',
                    },
                    {
                        action: btnShortenLoanClicked,
                        label: 'Raccourcir',
                    },
                    {
                        action: btnCancelLoanClicked,
                        label: 'Annuler',
                    },
                ]}
            />
            <hr />
            <h3>Emprunt en cours</h3>
            <LoansList
                loansData={loans}
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
            <h3>Demandes refusées et emprunts passés </h3>
            <LoansList
                loansData={loans}
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
export default withRouter(BorrowerLoans)
