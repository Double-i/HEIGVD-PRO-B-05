import React, {Fragment} from 'react'
import {Image, Media, Button, Row, Col} from 'react-bootstrap'
import {
    FaUser,
    FaRegCalendarAlt,
    FaLocationArrow,
} from 'react-icons/fa'
import * as moment from 'moment'
import {transformState} from "../../common/State";
import {formatString} from "../../common/Utils";
import {IMG_API_URL} from "../../common/ApiHelper";

/**
 * This method is used to display the adresse properly - Street, city (district - country)
 * @param address the address with the format used by the backend
 * @returns {string} the address string eg: Rue de la boulangerie 13 Concise (Vaud - Suisse)
 */
function formatAdress(address) {
    return `${address.address} ${address.city.city} (${address.district} - ${address.city.country.country})`
}

/**
 * Return the url to used -
 * @param tool
 * @returns {string}
 */
function getThumbnail(tool){
    if(tool.images.length > 0 )
    {
        return tool.images[0].pathToImage.toString();
    }
    else
        return "default.png";

}

function LoansListItem(props) {
    return (
        <Fragment>

                <Row className="justify-content-md-center">
                    <Col xs="4" sm="4" md="3" lg="2">
                        <Image
                            src={formatString("{0}/{1}",IMG_API_URL, getThumbnail(props.loan.ezobject))}
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
                        <h3>{props.loan.ezobject.name}</h3>
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
                                <h4>{props.loan.ezobject.name}</h4>
                            </Col>
                            <Col xs="6" sm="6" md="6" lg="6" xl="6">
                                <span style={{fontSize: 'smaller'}}>
                                    <FaUser/> {props.isOwner ? props.loan.borrower.userName : props.loan.ezobject.ownerUserName}
                                    <hr/>
                                    <FaLocationArrow/> {props.isOwner ? formatAdress(props.loan.borrower.address) : formatAdress(props.loan.owner.address)}
                                </span>
                            </Col>
                            <Col xs="6" sm="6" md="6" lg="6" xl="6">
                                <span>
                                    {transformState(props.loan.state, props.parentIdx)}
                                    <hr/>
                                    <FaRegCalendarAlt/> Du {moment(props.loan.validPeriod.dateStart).format("DD/MM/YYYY")} au {moment(props.loan.validPeriod.dateEnd).format("DD/MM/YYYY")}
                                </span>
                            </Col>
                        </Row>
                    </Col>
                    <Col xs="12" sm="12" md="12" lg="2" xl="2">
                        <span><br/></span>
                        {props.actionButtons.map((button, idx) => (
                            <Button
                                key={"ctrl-"+props.parentIdx + idx}
                                variant="outline-primary"
                                block
                                onClick={() => {
                                    button.action(props.loan)
                                }}
                            >
                                {button.label}
                            </Button>
                        ))}
                    </Col>
                </Row>
        

            <hr/>
        </Fragment>)
}

/**
 * List of loans used in loans management (borrower and owner)
 * @param props are :
 *                  1) props.loansData: loans data
 *                  2) props.isOwner: a boolean to know if it should disaply a owner or a borrower version
 *                  3) props.actionButtons: the list of buttons for a loan.
 * @returns {*}
 */
function LoansList(props) {
    return (
        <Fragment>
            {props.loansData.map((loan, idx) => (
                <LoansListItem loan={loan} parentIdx={idx} {...props} key={"loans-" + idx}/>
            ))}
        </Fragment>
    )
}

export default LoansList
