import {sendEzApiRequest} from "../common/ApiHelper"
import React from "react"

class ToolDetails extends React.Component{

    SEARCH_URI = '/objects'

    state = {
        description: "",
        name: "",
        images: {},
        objectTags: {},
        owner: {}
    }

    constructor(props)
    {
        super(props);

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
        return <div>
            <h1>{this.state.name}</h1>
            <p>{this.state.description}</p>
        </div>
    }
}

export default ToolDetails