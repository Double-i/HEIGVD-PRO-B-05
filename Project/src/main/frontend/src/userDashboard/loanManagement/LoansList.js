import React, { Fragment } from 'react'
import { Image, Media, Button, Row, Col } from 'react-bootstrap'
import {
    FaUser,
    FaRegClock,
    FaRegCalendarAlt,
    FaLocationArrow,
} from 'react-icons/fa'

// TODO :
//  - SI isOwner est true alors afficher l'emprunteur
//  - AUTREMENT, afficher le propriétaire

function LoansList(props) {
    return (
        <Fragment>
            {props.loansData.map((value, idx) => (
                <Fragment>
                    <Media as="li">
                        <Row className="justify-content-md-center">
                            <Col xs="4" sm="4" md="3" lg="2">
                                <Image
                                    src="tools_img_placeholder.png"
                                    rounded
                                    fluid
                                    style={{ maxWidth: '140px' }}
                                />
                            </Col>
                            <Col
                                className="d-block d-md-none"
                                xs="8"
                                sm="8"
                            >
                                <h3>Marteau à tête creuse</h3>
                            </Col>

                            <Col xs="12" sm="12" md="9" lg="8">
                                <Row>
                                    <Col
                                        className="d-none d-md-block"
                                        xs="12"
                                        sm="12"
                                        md="12"
                                        lg="12"
                                    >
                                        <h4>Marteau à tête creuse</h4>
                                    </Col>
                                    <Col xs="6" sm="6" md="6" lg="6">
                                        <span style={{ fontSize: 'smaller' }}>
                                            <FaUser /> Robert le Trolleur
                                            <hr />
                                            <FaLocationArrow /> Rue de la
                                            boulangerie 9, 1428 Provence Suisse
                                        </span>
                                    </Col>
                                    <Col xs="6" sm="6" md="6" lg="6">
                                        <span>
                                            <FaRegClock /> En attente
                                            <hr />
                                            <FaRegCalendarAlt /> Du 02.02.2020
                                            au 02.03.2020
                                        </span>
                                    </Col>
                                </Row>
                            </Col>

                            <Col xs="12" sm="12" md="12" lg="2">
                                <span><br/></span>
                                {props.actionButtons.map((button, key) => (
                                    <Button
                                        variant="outline-primary"
                                        block
                                        onClick={() => {
                                            button.action(value)
                                        }}
                                    >
                                        {button.label}
                                    </Button>
                                ))}
                            </Col>
                        </Row>
                    </Media>

                    <hr />
                </Fragment>
            ))}
        </Fragment>
    )
}
export default LoansList
