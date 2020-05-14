import * as React from "react";
import DisplayTool from "./displayTool";

class DisplayTools extends React.Component {

    constructor(props){
        super(props);
    }

    render(){

        return (
            <div className="container" style={{marginTop: '20px'}}>
                <div className="row border-bottom header">
                    <div className="col-2">
                        <div>Nom de l'outil</div>
                    </div>
                    <div className="col-4">
                        <div>Description</div>
                    </div>
                    <div className="col-2">
                        <div>Prêteur</div>
                    </div>
                    <div className="col-2">
                        <div>Tags</div>
                    </div>
                </div>
                {this.props.data.map((item, idx) => (

                    <DisplayTool
                        key={"search-item-"+item.id}
                        id={item.id}
                        name={item.name}
                        description={item.description}
                        ownerUserName={item.ownerUserName}
                        objectTags={item.objectTags}
                    >
                    </DisplayTool>
                ))}
            </div>
        )
    }
}

export default DisplayTools;