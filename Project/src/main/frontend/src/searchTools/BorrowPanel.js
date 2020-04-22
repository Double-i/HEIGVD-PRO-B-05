import {default as React, useContext, useState} from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import {sendEzApiRequest} from "../common/ApiHelper";

class BorrowPanel extends React.Component {

    sendLoansAPIEndpoint = "/loans"

    constructor(props){
        super(props);
        this.state = {
            dateRange : new Date()
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
            dateStart : beginDate.toString(),
            dateEnd : endDate.toString(),
            toolId : this.props.tool.id
        }).then(
            (response) => {
                alert("Requete envoyée!" + response.status)

            }, (error)=>{
                alert("Erreur dans l'envoi de la requete : " + error.errorCode);

                /*if(error.errorCode === 403 || error.errorCode === 404 ) {
                    alert("Erreur dans l'envoi de la requete : " + error.errorCode);
                } else{
                    alert("Erreur dans l'envoi de la requete : " + error.errorCode);
                }*/
            })
    }
    onChange(date){
        this.setState({dateRange : date});
    }

    render() {
        return (
            <Modal
                {...this.props}
                size="lg"
                aria-labelledby="contained-modal-title-vcenter"
                centered
            >
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">
                        Louer
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>

                    <p>
                        <h4>Outil : {this.props.tool.name} </h4>

                        <h5>Période d'emprunt :</h5> <br/>
                        <Calendar
                            onChange={this.onChange}
                            value={this.state.dateRange}
                            selectRange
                            minDate={new Date()}
                        />
                        <Button onClick={()=>this.sendValidation()}>Envoyer une demande</Button>

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