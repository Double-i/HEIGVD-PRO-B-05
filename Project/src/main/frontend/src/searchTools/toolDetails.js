import {sendEzApiRequest} from "../common/ApiHelper";
import React from "react"

class ToolDetails extends React.Component{

    SEARCH_URI = '/objects'

    constructor(props)
    {
        super(props);

        this.state = {
            description: "",
            name: "",
            images: {},
            objectTags: {},
            owner: {}
        }

    }

    async componentDidMount() {
        sendEzApiRequest(this.SEARCH_URI + '/' + this.props.match.params.id)
            .then((response) => {
                console.log(response);
                this.setState(response);
            })
            .catch(err => alert(err));
    }

    render()
    {
        return <ToolDetails>
            <h1>{this.state.name}</h1>
        </ToolDetails>
    }
}

export default ToolDetails