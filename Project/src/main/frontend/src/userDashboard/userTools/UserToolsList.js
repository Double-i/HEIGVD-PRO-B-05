import * as React from 'react'
import {Link} from "react-router-dom";
import {Button} from "react-bootstrap";
import Container from "react-bootstrap/Container";
import {sendEzApiRequest} from "../../common/ApiHelper";
import DisplayTools from "../../toolsUtil/displayTools";


class UserToolsList extends React.Component {

    GET_TOOLS_URI = '/objects/myObjects';

    constructor(props){
        super(props);
        this.state = {
            tools : []
        }
    }

    removeTool(tool){
        const newToolsList = [...this.state.tools]
        const idxTool = newToolsList.indexOf(tool)
        if(idxTool !== -1){
            newToolsList.splice(idxTool,1)
            this.setState({tools: newToolsList})
        }
    }

    componentDidMount() {
        //get user tools
        sendEzApiRequest(this.GET_TOOLS_URI)
            .then((response) => {
                if (response.status === "403") {
                    console.log("Pas réussi à fetch les outils utilisateur");
                } else {
                    console.log(response)
                    this.setState({tools: response})
                }
            }, error => {
                console.log(error)
            })

    }
    render() {

        return (
            <Container>
                <Link to="/dashboard/addTool"><Button variant="primary">Ajouter un nouvel outil</Button></Link>
                <DisplayTools
                    data = {this.state.tools}
                    hideOwner={true}
                    hideBorrowButton={true}
                    hideEditButton={false}
                    deleteButtonCB={(tool) => this.removeTool(tool)}
                />
            </Container>
        )
    }
}

export default UserToolsList;