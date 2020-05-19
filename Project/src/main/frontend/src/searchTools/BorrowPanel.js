import {default as React} from "react";
import {Button, Modal, Alert} from "react-bootstrap";
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import {sendEzApiRequest} from "../common/ApiHelper";
import * as moment from "moment"

class BorrowPanel extends React.Component {

    sendLoansAPIEndpoint = "/loans"

    constructor(props) {
        super(props);
        this.state = {
            dateRange: new Date(),
            response: {
                    show : false,
                    type:"",
                    message: ""
                }
        }
        this.onChange = this.onChange.bind(this);
        this.sendValidation = this.sendValidation.bind(this)
    }

    sendValidation() {
        let beginDate = this.state.dateRange;
        let endDate
        //React calendar provide either an array or a single value , depends of the user choice (range choice or not)
        if (beginDate instanceof Array) {
            endDate = beginDate[1].toISOString().slice(0, 10)
            beginDate = beginDate[0].toISOString().slice(0, 10)
        } else {
            beginDate = beginDate.toISOString().slice(0, 10)
            endDate = beginDate
        }

        sendEzApiRequest(this.sendLoansAPIEndpoint, "POST", {
            dateStart: moment(beginDate).add('1', 'days').format('YYYY-MM-DD'),
            dateEnd: endDate.toString(),
            toolId: this.props.tool.id
        }).then(
            (response) => {
                this.setState({response: {
                        show : true,
                        type:"success",
                        message: "Demande d'emprunt effectuée !"
                    }})

            }, (error) => {
                this.setState({response: {
                        show : true,
                        type:"warning",
                        message: "L'outil est déjà occupé pendant cette période"
                    }})
            })
    }

    onChange(date) {
        this.setState({dateRange: date});
    }

    render() {
        return (
            <Modal
                {...this.props}
                size="lg"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                onShow={()=>{
                    this.setState({response: {
                            show : false,
                            type:"warning",
                            message: ""
                        }})
                }}
            >
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">
                        Louer
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Alert variant={this.state.response.type} show={this.state.response.show}>
                        {this.state.response.message}
                    </Alert>
                    <p>
                        <h4>Outil : {this.props.tool.name} </h4>

                        <h5>Période d'emprunt :</h5> <br/>
                        <Calendar
                            onChange={this.onChange}
                            value={this.state.dateRange}
                            selectRange
                            minDate={moment().add(1, 'days').toDate()}
                        />
                        <Button onClick={() => this.sendValidation()}>Envoyer une demande</Button>

                    </p>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.props.onHide}>Close</Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

export default BorrowPanel;