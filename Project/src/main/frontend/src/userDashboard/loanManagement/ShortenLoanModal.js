import React, {useContext, useEffect, useState} from 'react'
import {Button, Container, Modal, Table} from 'react-bootstrap'
import LoansList from './LoansList'
import * as moment from "moment";
import {sendEzApiRequest} from "../../common/ApiHelper";
import {STATE, transformState} from "../../common/State";

function ShortenLoanModal(props) {
    return (
        <Modal
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
            onHide={props.onHide}
            show={props.show}
        >
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Demande de raccourcissement
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <h5>Mes demandes</h5>
                <Table striped bordered hover>
                    <tbody>
                    {
                        props.periods.filter(period => period.creator === "owner" && period.state !== STATE.accepted)
                            .map((period, idx) => {
                                return (
                                    <tr>
                                        <td>{moment(period.dateStart).format("DD/MM/YYYY")}</td>
                                        <td>{moment(period.dateEnd).format("DD/MM/YYYY")}</td>
                                        <td>{moment(period.dateEnd).isBefore(moment()) ? transformState(STATE.passed) : transformState(period.state)}</td>
                                        <td>
                                            <Button
                                                variant="outline-primary"
                                                block
                                                disabled={moment(period.dateEnd).isBefore(moment())}
                                                onClick={() =>
                                                    props.cancelPeriod(period)
                                                }
                                            >Annuler</Button>
                                        </td>
                                    </tr>)
                            })
                    }
                    </tbody>
                </Table>
                <h5>Ses demandes</h5>
                <Table striped bordered hover>
                    <tbody>
                    {
                        props.periods.filter(period => period.creator === "borrower" && period.state !== STATE.accepted)
                            .map((period, idx) => {

                                return (
                                    <tr>
                                        <td>{moment(period.dateStart).format("DD/MM/YYYY")}</td>
                                        <td>{moment(period.dateEnd).format("DD/MM/YYYY")}</td>
                                        <td>{transformState(period.state)}</td>
                                        <td>
                                            <Button
                                                variant="outline-primary"
                                                block
                                                disabled={moment(period.dateEnd).isBefore(moment())}
                                                onClick={() =>
                                                    props.acceptPeriod(period)
                                                }
                                            >Accepter</Button>
                                            <Button
                                                variant="outline-primary"
                                                block
                                                disabled={moment(period.dateEnd).isBefore(moment())}
                                                onClick={() => props.refusePeriod(period)}
                                            >Refuser</Button>
                                        </td>

                                    </tr>)
                            })
                    }

                    </tbody>
                </Table>
            </Modal.Body>
        </Modal>
    )
}

export default ShortenLoanModal
