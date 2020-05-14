import * as React from "react";
import DisplayTool from "./displayTool";

/**
 * Display tools as table, given from props
 */
class DisplayTools extends React.Component {

    constructor(props){
        super(props);
    }

    render(){

        return (

            <div className="content">
                {/* Result */}
                <div className="container" style={{marginTop: '20px'}}>
                    <div className="row border-bottom header">
                        <div className="col-2">
                            <div>Image</div>
                        </div>
                        <div className="col-2">
                            <div>Nom de l'outil</div>
                        </div>
                        <div className="col-2">
                            <div>Description</div>
                        </div>
                        <div className="col-2" hidden={this.props.hideOwner}>
                            <div>Prêteur</div>
                        </div>
                        <div className="col-2">
                            <div>Tags</div>
                        </div>
                    </div>
                    {
                        this.props.data.length > 0 ?(
                        this.props.data.map(item => (
                        <DisplayTool
                            id={item.id}
                            name={item.name}
                            description={item.description}
                            ownerUserName={item.owner.userName}
                            objectTags={item.objectTags}
                            images={item.images}
                            hideOwner={this.props.hideOwner}
                            hideBorrowButton={this.props.hideBorrowButton}
                            hideEditButton={this.props.hideEditButton}
                            hideDeleteButton={this.props.hideDeleteButton}
                        >
                        </DisplayTool>
                        )
                    )) : (<></>)}
                </div>
            </div>
        )
    }
}

export default DisplayTools;