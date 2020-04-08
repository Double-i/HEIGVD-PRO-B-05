import React, { useState } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import { Button, Container, Row, Col } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import LoansList from './LoansList'

function OwnerLoans(props) {
    const loans = [1, 2, 3, 4]
    return (
        <Container>
            <h3>Demande d'emprunt en attente</h3>
            <LoansList
                loansData={loans}
                isOwner={true}
                actionButtons={[
                    {
                        action: (loanId) => {
                            console.log("Afficher le loan id: ", loanId)
                        },
                        label : "Afficher"
                    },{
                        action: (loanId) => {
                            console.log("Raccourcir le loan id: ", loanId)
                        },
                        label : "Raccourcir"
                    },{
                        action: (loanId) => {
                            console.log("Annuler le loan id: ", loanId)
                        },
                        label : "Annuler"
                    }
                ]}
            />
            <hr/>
            <h3>Emprunt en cours</h3>
            <LoansList
                loansData={loans}
                actionButtons={[
                    {
                        action: (loanId) => {
                            console.log("Afficher le loan id: ", loanId)
                        },
                        label : "Afficher"
                    },{
                        action: (loanId) => {
                            console.log("Raccourcir le loan id: ", loanId)
                        },
                        label : "Raccourcir"
                    }
                ]}
            />
             <hr/>
            <h3>Demandes refusées et emprunts passés </h3>
            <LoansList
                loansData={loans}
                actionButtons={[
                    {
                        action: (loanId) => {
                            console.log("Afficher le loan id: ", loanId)
                        },
                        label : "Afficher"
                    }
                ]}
            />
        </Container>
    )
}
export default BorrowerLoans
