import Button from "react-bootstrap/Button";
import * as React from "react";

class DisplayTool extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            isBorrowable : true
        }
    }

    render() {
        return (
            <div className="row border-bottom" key={"itemId" + this.props.id}>
                <div className="col-2">
                    <div>{this.props.name}</div>
                </div>
                <div className="col-4">
                    <div>{this.props.description}</div>
                </div>
                <div className="col-2">
                    <div>{this.props.ownerUserName}</div>
                </div>
                <div className="col-2">
                    <div>
                        {this.props.objecttag.map(tag =>(
                            <li>{tag.name}</li>
                        ))}
                    </div>
                </div>
                <div className="col-2">
                    <Button
                        disabled = {this.state.isBorrowable}
                    >
                        Emprunter
                    </Button>
                </div>
            </div>
        )
    }
}

export default DisplayTool;