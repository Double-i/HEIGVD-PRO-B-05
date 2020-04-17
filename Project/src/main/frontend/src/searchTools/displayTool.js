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
                        {
                            this.props.objectTags.map(tag =>(
                            <li key={tag.id}>{tag.name}</li>
                            ))
                        }
                    </div>
                </div>
                <div className="col-2" key={"DivButtonId" + this.props.id}>
                    <Button
                        disabled = {this.state.isBorrowable}
                        key={"buttonId" + this.props.id}
                    >
                        Emprunter
                    </Button>
                </div>
            </div>
        )
    }
}

export default DisplayTool;