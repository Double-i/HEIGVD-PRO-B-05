import {default as React} from "react";
import {Button, Dropdown, Modal} from "react-bootstrap";
import 'react-calendar/dist/Calendar.css';
import {sendEzApiRequest} from "../common/ApiHelper";


class ReportPanel extends React.Component {
    REPORTSFLAGS = '/reports/types'
    ADDREPORT = '/reports'
    constructor(props) {
        super(props);
        this.state = {
            response: {
                show : false,
                type:"",
                message: ""
            },
            reportFlags:[],
            selectedFlags:[],
            reason:""

        }
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    handleSubmit()
    {
        sendEzApiRequest(this.ADDREPORT,'POST',{
            reportType : this.state.reason,
            toolId : this.props.toolId,
            dateReport : Date.now()
        })
            .then(
                (result) => {
                    this.setState({tools : result})
                },
                error => {
                    console.log('Error occurs ', error)
                })
    }

    render() {
        return (
            <Modal
                {...this.props}
                size="sm"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                onShow={()=>{
                    sendEzApiRequest(this.REPORTSFLAGS)
                        .then( (response) => {
                            //Get tags from db
                            if(response.status === 403) {
                                console.log('pas reussi a fetch les signalements...');
                            }else{
                                this.setState({reportFlags: response.map((value) => value)})
                            }
                        }, error => {
                            console.log(error)
                        })

                    this.setState({response: {
                            show : false,
                            type:"warning",
                            message: ""
                        }})
                }}>
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">
                        Signaler
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>

                    <Dropdown>
                        <Dropdown.Toggle variant="primary" id="dropdown-basic">
                            Pourquoi?
                        </Dropdown.Toggle>
                        <p>{this.state.reason}</p>
                        <Dropdown.Menu>
                            {this.state.reportFlags.map((value, idx) => (
                                <Dropdown.Item
                                    key={`tag-${idx}`}
                                    value={value}
                                    onClick={()=>{
                                        this.setState({reason:value})
                                    }}
                                >
                                    {value}
                                </Dropdown.Item>
                            ))}
                        </Dropdown.Menu>
                    </Dropdown>

                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={
                        () => {
                            this.handleSubmit()
                            this.props.onHide()
                        }}>Signaler</Button>
                    <Button variant="danger" onClick={
                        () => {
                            this.props.onHide()
                        }}>Annuler</Button>
                </Modal.Footer>

            </Modal>
        )
    }

}
export default ReportPanel;