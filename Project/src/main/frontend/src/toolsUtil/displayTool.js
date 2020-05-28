import Button from "react-bootstrap/Button";
import * as React from "react";
import BorrowPanel from "../searchTools/BorrowPanel";
import EditToolPanel from "../userDashboard/editTools/EditToolPanel";
import {IMG_API_URL, sendEzApiRequest} from "../common/ApiHelper";
import {formatString} from "../common/Utils";
import {withRouter} from "react-router-dom"

/**
 * Display a tool
 * Props :
 * id={item.id}
 * name={item.name}
 * description={item.description}
 * ownerUserName={item.ownerUserName}
 * objectTags={item.objectTags}
 * images={item.images}
 * hideOwner={boolean}
 * hideBorrowButton={boolean}
 * hideEditButton={boolean}
 * hideDeleteButton={boolean}
 */
class DisplayTool extends React.Component {

    DELETE_ITEM_URI = '/objects/delete/'
    thumbnail = 'default.png'

    constructor(props) {
        super(props);
        this.state = {
            isBorrowable: true,
            borrowModalShow: false,
            editModalShow: false,

        }
        if (this.props.images.length > 0) {
            this.thumbnail = this.props.images[0].pathToImage.toString();
        } else
            this.thumbnail = "default.png";
    }


    componentWillReceiveProps(nextProps, nextContext) {
        if (nextProps.images.length > 0) {
            this.thumbnail = nextProps.images[0].pathToImage.toString();
        } else
            this.thumbnail = "default.png";
    }

    /**
     * Display the borrow pannel
     * @param value
     */
    setBorrowModalShow(value) {
        this.setState({borrowModalShow: value});
    }

    /**
     * Display the edit pannel show
     * @param value
     */
    setEditModalShow(value) {
        this.setState({editModalShow: value})
    }

    /**
     * Delete item, using the DELETE http request
     */
    deleteItem() {
        if (window.confirm('Etes-vous sur de vouloir effacer cet outil ?')) {
            sendEzApiRequest(this.DELETE_ITEM_URI + this.props.id, "DELETE")
                .then((response) => {
                    this.props.deleteButtonCB(this.props.tool)
                }, error => {
                    alert("Impossible de suprimer l'outil")

                    console.log(error)
                })
        }
    }

    render() {
        return (
            <div className="row border-bottom" style={{marginTop: '10px'}} key={"itemId" + this.props.id}>

                <div className="col-2">
                    <img
                        style={{width: '100px', height: '100px'}}
                        src={formatString("{0}/{1}", IMG_API_URL, this.thumbnail)}
                    />
                </div>
                <div className="col-2">
                    <div>{this.props.name}</div>
                </div>
                <div className="col-2">
                    <div>{this.props.description}</div>
                </div>
                <div className="col-2" hidden={this.props.hideOwner}>
                    <div>{this.props.ownerUserName}</div>
                </div>
                <div className="col-2" style={{marginBottom: '10px'}}>
                    <div>
                        {
                            this.props.objectTags.map(tag => (
                                <li key={"search-item-tag-" + tag.name}>{tag.name}</li>
                            ))
                        }
                    </div>
                </div>
                <div className="col-2" key={"DivButtonId" + this.props.id} hidden={this.props.hideBorrowButton}>
                    <Button
                        disabled={false} //TODO : avoir une props de l'item isBorrowable !
                        key={"btn-display-" + this.props.id}
                        style={{
                            marginBottom: '10px'
                        }}
                        onClick={() => this.props.history.push(formatString("/tooldetails/{0}", this.props.id))}
                    >
                        Voir outil
                    </Button>
                    <Button
                        disabled={false} //TODO : avoir une props de l'item isBorrowable !
                        key={"buttonId" + this.props.id}
                        style={{
                            marginBottom: '10px'
                        }}
                        onClick={() => this.setBorrowModalShow(true)}
                    >
                        Emprunter
                    </Button>
                    <BorrowPanel
                        show={this.state.borrowModalShow}
                        onHide={() => this.setBorrowModalShow(false)}
                        tool={this.props}
                    />
                </div>

                <div className="col-2" key={"DivEditButtonId" + this.props.id} hidden={this.props.hideEditButton}>
                    <Button
                        key={"editButtonId" + this.props.id}
                        style={{
                            marginBottom: '10px'
                        }}
                        onClick={() => this.setEditModalShow(true)}
                    >
                        Editer l'outil
                    </Button>

                    <EditToolPanel
                        show={this.state.editModalShow}
                        onHide={() => this.setEditModalShow(false)}
                        tool={this.props}
                    />
                </div>


                <div className="col-2" key={"DivDeleteButtonId" + this.props.id} hidden={this.props.hideDeleteButton}>
                    <Button
                        variant="danger"
                        key={"editButtonId" + this.props.id}
                        style={{
                            marginBottom: '10px',
                        }}
                        onClick={() => this.deleteItem()}
                    >
                        Supprimer l'outil
                    </Button>
                </div>

            </div>
        )
    }
}

export default withRouter(DisplayTool);