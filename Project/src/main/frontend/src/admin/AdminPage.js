import * as React from "react";
import {sendEzApiRequest} from "../common/ApiHelper";
import DisplayTools from "../toolsUtil/displayTools";

class AdminPage extends React.Component{


    //TODO : Manage users too ?!

    //TODO : only reported tools
    ALL_TOOLS_URI = '/objects'

    constructor(props){
        super(props)
        this.state = {
            users : [],
            tools : [],
            showTools : false,
            showUsers : false
        }
    }

    componentDidMount() {
        //get users
        sendEzApiRequest(this.ALL_TOOLS_URI)
            .then((response) =>{
                this.setState({tools:response})
            })
        //get tools

    }
    render() {
        return (
            <div className={'container'}>
                <DisplayTools
                    data = {this.state.tools}
                    hideOwner={false}
                    hideBorrowButton={true}
                    hideEditButton={true}
                    hideDeleteButton={false}
                />
            </div>
        );
    }
}

export default AdminPage