import React, {Fragment} from 'react'
import {Image, Media, Button, Row, Col} from 'react-bootstrap'
import {
    FaUser,
    FaRegClock,
    FaRegCalendarAlt,
    FaLocationArrow,
} from 'react-icons/fa'
import * as moment from 'moment'
import {transformState} from "../../common/State";

function formatAdress(address){
    return `${address.address} ${address.city.city} (${address.district} - ${address.city.country.country})`
}

function LoansList(props) {
    return (
        <Fragment>
            {props.loansData.map((value, idx) => (
                <Fragment key={"loan-id-" + idx}>
                    <Media as="li">
                        <Row className="justify-content-md-center">
                            <Col xs="4" sm="4" md="3" lg="2">
                                <Image
                                    src="/tools_img_placeholder.png"
                                    rounded
                                    fluid
                                    style={{maxWidth: '140px'}}
                                />
                            </Col>
                            <Col
                                className="d-block d-md-none"
                                xs="8"
                                sm="8"
                            >
                                <h3>{value.ezobject.name}</h3>
                            </Col>

                            <Col xs="12" sm="12" md="9" lg="8" xl="8">
                                <Row>
                                    <Col
                                        className="d-none d-md-block"
                                        xs="12"
                                        sm="12"
                                        md="12"
                                        lg="12"
                                        xl="12"
                                    >
                                        <h4>{value.ezobject.name}</h4>
                                    </Col>
                                    <Col xs="6" sm="6" md="6" lg="6" xl="6">
                                        <span style={{fontSize: 'smaller'}}>

                                            <FaUser/> {props.isOwner ? value.borrower.userName : value.ezobject.ownerUserName}
                                            <hr/>
                                            <FaLocationArrow/> {props.isOwner ? "TODO" : formatAdress(value.borrower.address) }
                                        </span>
                                    </Col>
                                    <Col xs="6" sm="6" md="6" lg="6" xl="6">
                                        <span>
                                            {transformState(value.state)}
                                            <hr/>
                                            <FaRegCalendarAlt/> Du {moment(value.validPeriod.dateStart).format("DD/MM/YYYY")} au {moment(value.validPeriod.dateEnd).format("DD/MM/YYYY")}
                                        </span>
                                    </Col>
                                </Row>
                            </Col>
                            <Col xs="12" sm="12" md="12" lg="2" xl="2">
                                <span><br/></span>
                                {props.actionButtons.map((button, key) => (
                                    <Button
                                        key={"ctrl-"+key}
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

                    <hr/>
                </Fragment>
            ))}
        </Fragment>
    )
}

export default LoansList