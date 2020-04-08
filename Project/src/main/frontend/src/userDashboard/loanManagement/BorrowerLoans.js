import React, { useState } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import {  Container } from 'react-bootstrap'
import { withRouter } from 'react-router-dom'
import LoansList from './LoansList'

function BorrowerLoans(props) {
    const loans = [1, 2, 3, 4]
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
