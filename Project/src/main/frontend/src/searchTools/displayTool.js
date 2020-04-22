import Button from "react-bootstrap/Button";
import * as React from "react";
import BorrowPanel from "./BorrowPanel";


class DisplayTool extends React.Component {

    modalShow;
    setModalShow;

    constructor(props){
        super(props);
        this.state = {
            isBorrowable : true,
            modalShow : false
        }
    }

    setModalShow(value){
        this.setState({modalShow : value});
        console.log("modalShow = " + this.state.modalShow)
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
                <div className="col-2">
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
                <div className="col-2" key={"DivButtonId" + this.props.id}>
                    <Button
                        disabled = {false} //TODO : avoir une props de l'item isBorrowable !
                        key={"buttonId" + this.props.id}
                        style={{
                            marginBottom: '10px'
                        }}
                        onClick = {() => this.setModalShow(true)}
                    >
                        Emprunter
                    </Button>
                    <BorrowPanel
                        show={this.state.modalShow}
                        onHide={() => this.setModalShow(false)}
                        tool = {this.props}
                    />
                </div>
            </div>
        )
    }
}

export default DisplayTool;