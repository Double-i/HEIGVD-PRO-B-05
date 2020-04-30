import * as React from "react";
import {sendEzApiRequest} from "../common/ApiHelper";
import DisplayTools from "../toolsUtil/displayTools";
import Button from "react-bootstrap/Button";

class AdminPage extends React.Component{


    //TODO : Manage users too ?!

    //TODO : only reported tools
    ALL_TOOLS_URI = '/objects'
    ALL_USER_URI = '/users'

    constructor(props){
        super(props)
        this.state = {
            users : [],
            tools : [],
            hideTools : false,
            hideUsers : true,
        }
    }

    componentDidMount() {
        //get tools
        sendEzApiRequest(this.ALL_TOOLS_URI)
            .then((response) =>{
                this.setState({tools:response})
            })
        //get user
        sendEzApiRequest(this.ALL_USER_URI)
            .then((response) =>{
                this.setState({users:response})
                console.log(response)
            })
    }

    deleteUser(user){
        if (window.confirm('Etes-vous sur de vouloir supprimer cet utilisateur ?')){
            sendEzApiRequest(this.ALL_USER_URI + "/" + user, "DELETE")
                .then((response) =>{
                    console.log(response)
                })
        }
    }

    render() {
        return (
            <div className={'container'}>
                <Button
                    onClick={() => this.setState({hideUsers : false, hideTools : true })}
                >
                    Liste utilisateur
                </Button>
                <Button
                    onClick={() => this.setState({hideUsers : true, hideTools : false })}
                >
                    Liste outils
                </Button>
                <div hidden={this.state.hideTools}>
                    <DisplayTools
                        data = {this.state.tools}
                        hideOwner={false}
                        hideBorrowButton={true}
                        hideEditButton={true}
                        hideDeleteButton={false}
                    />
                </div>
                <div hidden={this.state.hideUsers}>
                    { this.state.users.map((user)=>{
                        return (
                            <div className="row border-bottom" style={{marginTop: '10px'}} key={"userId" + user.userName}>
                                <div className="col-10">
                                    {user.userName}
                                </div>
                                <div className="col-2">
                                    <Button
                                        variant="danger"
                                        onClick={() => this.deleteUser(user.userName)}
                                        key={"deleteButton"+user.userName}
                                    >Effacer l'utilisateur</Button>
                                </div>
                            </div>
                        )
                    })}
                </div>
            </div>
        );
    }
}

export default AdminPage