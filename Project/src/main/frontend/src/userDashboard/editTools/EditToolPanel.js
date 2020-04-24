import {default as React} from "react";
import {sendEzApiRequest} from "../../common/ApiHelper";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import ToolForm from "../../toolsUtil/toolForm";

class EditToolPanel extends React.Component {

    sendLoansAPIEndpoint = "/..."

    constructor(props){
        super(props);
        this.state = {
        }
        this.onChange = this.onChange.bind(this);
        this.sendValidation = this.sendValidation.bind(this)
    }

    sendValidation() {

    }
    onChange(date){

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
                        Editer
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <p>
                        <h4>Outil : {this.props.tool.name} </h4>
                        <ToolForm
                            tool = {this.props.tool}
                            formTitle={"Modifier"}
                            action={"update"}
                        />
                    </p>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.props.onHide}>Close</Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

export default EditToolPanel;