import { Map, GoogleApiWrapper, Marker} from 'google-maps-react';
import React from "react"
import {sendEzApiRequest} from "../common/ApiHelper";

const mapStyles = {
    width:  '100%'
}

class MapContainer extends React.Component {

    constructor(props)
    {
        super(props);

        this.state = {
            search : '',
            tools : [],
            tags : [],
            searchTags : []
        }

        sendEzApiRequest(this.SEARCH_URI)
        .then((response) =>{
            if(response.status === 403)
            {
                console.log("Tools not found !");
            }
            else
            {
                console.log(response);
                this.setState({tools:response});
                console.log(this.state.tools);
            }
        })
    }

    getMarkers()
    {
        return this.state.tools.map(tool => {
            return <Marker position={{
             lat: tool.latitude,
             lng: tool.longitude
            }} />
        })
    }

    render()
    {
        return <Map
                 google={this.props.google}
                 zoom={10}
                 style={mapStyles}
                 initialCenter={{ lat: 46.5, lng: 6.5}}
               >
               {this.getMarkers()}
               </Map>

    }
}

export default GoogleApiWrapper({
  apiKey: 'AIzaSyCwmIS0mKIfWPvmZke7plXkeR1uZ6ahwcU'
})(MapContainer);
