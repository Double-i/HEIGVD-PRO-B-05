import React from 'react'
import {Button, Modal, Table} from 'react-bootstrap'
import * as moment from "moment";
import {STATE, transformState} from "../../common/State";
import {oppositeRole} from "../../common/Role";

/**
 * Display the different periods proposals by the owner or the borrower
 * It allows the user to accept/refuse the other user of the loan (borrower or owner) or to cancel his own proposal
 *
 * @param props
 * @returns {*}
 * @constructor
 */
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
                        props.loan.periods.filter(period => period.creator === props.role && period.state !== STATE.accepted)
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
                                                disabled={moment(period.dateEnd).isBefore(moment()) || period.state === STATE.cancel || period.state === STATE.refused}
                                                onClick={() =>
                                                    props.cancelPeriod(props.loan, period)
                                                }
                                            >Annuler</Button>
                                        </td>
                                    </tr>
                                )
                            })
                    }
                    </tbody>
                </Table>
                <h5>Ses demandes</h5>
                <Table striped bordered hover>
                    <tbody>
                    {
                        props.loan.periods.filter(period => period.creator === oppositeRole(props.role) && period.state !== STATE.accepted)
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
                                                disabled={moment(period.dateEnd).isBefore(moment()) || period.state === STATE.cancel || period.state === STATE.refused}
                                                onClick={() =>
                                                    props.acceptPeriod(props.loan, period)
                                                }
                                            >Accepter</Button>
                                            <Button
                                                variant="outline-primary"
                                                block
                                                disabled={moment(period.dateEnd).isBefore(moment()) || period.state === STATE.cancel || period.state === STATE.refused}
                                                onClick={() => props.refusePeriod(props.loan, period)}
                                            >Refuser</Button>
                                        </td>

                                    </tr>
                                )
                            })
                    }
                    </tbody>
                </Table>
            </Modal.Body>
        </Modal>
    )
}

export default ShortenLoanModal
