import * as React from 'react'
import Condition from "yup/lib/Condition";
import {Link} from "react-router-dom";
import {Button, Card} from "react-bootstrap";
import Container from "react-bootstrap/Container";
import {sendEzApiRequest} from "../../common/ApiHelper";
import DisplayTools from "../../toolsUtil/displayTools";


class ToolsList extends React.Component {

    GET_TOOLS_URI = '/objects/owner/test';

    constructor(props){
        super(props);
        this.state = {
            tools : []
        }
    }


    componentDidMount() {
        //get user tools
        sendEzApiRequest(this.GET_TOOLS_URI)
            .then((response) => {
                if (response.status == "403") {
                    console.log("Pas réussi a fetch les outils utilisateur");
                } else {
                    console.log(response)
                    this.setState({tools: response})
                }
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
                />
            </Container>
        )
    }
}

export default ToolsList;