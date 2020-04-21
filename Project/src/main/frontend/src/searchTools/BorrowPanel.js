import {default as React, useContext, useState} from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import {sendEzApiRequest} from "../common/ApiHelper";
import { SessionContext } from '../common/SessionHelper'

class BorrowPanel extends React.Component {

    //TODO : mettre le bon endpoint
    sendLoansAPIEndpoint = "..."
    username = ""

    constructor(props){
        super(props);
        this.state = {
            username : '',
            dateRange : new Date()
        }
        this.onChange = this.onChange.bind(this);
        this.sendValidation = this.sendValidation.bind(this)
    }

    sendValidation() {
        let beginDate = this.state.dateRange;
        let endDate
        if (beginDate instanceof Array) {
            endDate = beginDate[1].toISOString().slice(0, 10)
            beginDate = beginDate[0].toISOString().slice(0, 10)
        } else {
            beginDate = beginDate.toISOString().slice(0, 10)
            endDate = beginDate
        }

        let dataDate = {beginDate, endDate}
        let itemId = this.props.tool.id;
        //TODO : Envoyer la bonne requete POST avec dataDate & itemId + userid ?
        sendEzApiRequest(this.sendLoansAPIEndpoint, "POST", {
            username : this.username,
            item : itemId,
            rangeDate : dataDate
        }).then(
            (response) => {
                //Get tags from db
                if(response.status === 403 || response.status === 404 ) {
                    alert("Erreur dans l'envoi de la requete : " + response.status);
                }else{
                    alert("Requete envoyée!" + response.status)
                }
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
              <SessionContext.Consumer>
                    {({ session }) => {
                        //Maurice : J'ai fais comme ça parce que je peux pas utiliser les Hook dans des classes :/
                        if (session.isUserLogin()) {
                            this.username = session.getUserName();
                        }else{
                            console.log("vous n'avez rien a faire ici zomg!")
                        }}}
                </SessionContext.Consumer>

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