import Button from "react-bootstrap/Button";
import * as React from "react";
import BorrowPanel from "../searchTools/BorrowPanel";
import EditToolPanel from "../userDashboard/editTools/EditToolPanel";


class DisplayTool extends React.Component {

    modalShow;
    setModalShow;

    constructor(props){
        super(props);
        this.state = {
            isBorrowable : true,
            borrowModalShow : false,
            editModalShow : false
        }
        console.log(this.props.ownerUserName)
    }

    setBorrowModalShow(value){
        this.setState({borrowModalShow : value});
    }

    setEditModalShow(value){
        this.setState({editModalShow: value})
    }

    render() {
        return (
            <div className="row border-bottom" style={{marginTop: '10px'}} key={"itemId" + this.props.id}>
                <div className="col-2">
                    <div>{this.props.name}</div>
                </div>
                <div className="col-4">
                    <div>{this.props.description}</div>
                </div>
                <div className="col-2" hidden={this.props.hideOwner}>
                    <div>{this.props.ownerUserName}</div>
                </div>
                <div className="col-2" style={{marginBottom: '10px'}}>
                    <div>
                        {
                            this.props.objectTags.map(tag =>(
                            <li key={tag.id}>{tag.name}</li>
                            ))
                        }
                    </div>
                </div>
                <div className="col-2" key={"DivButtonId" + this.props.id} hidden={this.props.hideBorrowButton}>
                    <Button
                        disabled = {false} //TODO : avoir une props de l'item isBorrowable !
                        key={"buttonId" + this.props.id}
                        style={{
                            marginBottom: '10px'
                        }}
                        onClick = {() => this.setBorrowModalShow(true)}
                    >
                        Emprunter
                    </Button>
                    <BorrowPanel
                        show={this.state.borrowModalShow}
                        onHide={() => this.setBorrowModalShow(false)}
                        tool = {this.props}
                    />
                </div>

                <div className="col-2" key={"DivEditButtonId" + this.props.id} hidden={this.props.hideEditButton}>
                    <Button
                        key={"editButtonId" + this.props.id}
                        style={{
                            marginBottom: '10px'
                        }}
                        onClick = {() => this.setEditModalShow(true)}
                    >
                        Editer l'outil
                    </Button>

                    <EditToolPanel
                        show={this.state.editModalShow}
                        onHide={() => this.setEditModalShow(false)}
                        tool = {this.props}
                    />
                </div>

            </div>
        )
    }
}

export default DisplayTool;