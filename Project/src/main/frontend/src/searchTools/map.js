import { Map, GoogleApiWrapper, Marker, InfoWindow} from 'google-maps-react';
import React from "react"
import {sendEzApiRequest} from "../common/ApiHelper";
import { usePosition } from 'use-position';

const mapStyles = {
    width :  '80%'
}

/*export function getUserPos(Component) {
    return function WrappedComponent(props) {
        const position = usePosition();
        return <Component {...props} position={usePosition()} />;
    }
}*/

export class MapContainer extends React.Component {

    SEARCH_URI = '/objects'
    TAGS_URI = '/tags'

    constructor(props)
    {
        console.log(props);
        super(props);

        this.state = {
            search : '',
            tools : props.tools,
            tags : [],
            searchTags : [],
            showingInfoWindow: false,
            activeMarker: {},
            selectedTool: {},
            initialPosition : {}
        }


    }

    componentDidMount() {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const initialPosition = JSON.stringify(position);
                this.setState({ initialPosition });
                console.log(this.state.initialPosition);
            },
            (error) => alert(error.message),
            { enableHighAccuracy: true, timeout: 20000, maximumAge: 1000 }
        );
    }

    onMarkerClick = (props, marker, e) => {
        console.log("Clicked on marker !");
        this.setState({
            selectedTool: this.state.tools.find(tool => tool.name ===props.name),
            activeMarker: marker,
            showingInfoWindow: true
        });
    }

    onMapClicked = (props) => {
        if (this.state.showingInfoWindow) {
            this.setState({
                showingInfoWindow: false,
                activeMarker: null
            })
        }
    };

    getInfoWindow()
    {
        return <InfoWindow
        marker = {this.state.activeMarker}
        visible = {this.state.showingInfoWindow}>
        <div>
        <h1>{this.state.selectedTool === undefined ? "" : this.state.selectedTool.name}</h1>
        <p>
            {this.state.selectedTool === undefined ? "" : this.state.selectedTool.description}
        </p>
        </div>
        </InfoWindow>;
    }

    getMarkers()
    {
        return this.props.tools.map(tool => {
            console.log('getting tool : '+tool.name+' at '+tool.owner.address);
            return <Marker onClick = {this.onMarkerClick}
                           key = {tool.name}
                           name = {tool.name}
                           position={{
                             lat: tool.owner.address.lat,
                             lng: tool.owner.address.lng
                           }}/>
        })
    }

    render()
    {
        return <Map
                 key={0}
                 google={this.props.google}
                 zoom={10}
                 style={mapStyles}
                 initialCenter={{ lat: /*this.props.position.lat*/40, lng: /*this.props.position.lng*/40}}
                 onClick={this.onMapClicked}
               >
               {this.getMarkers()}
                {this.getInfoWindow()}
               </Map>

    }
}

export default GoogleApiWrapper({
  apiKey: 'AIzaSyCwmIS0mKIfWPvmZke7plXkeR1uZ6ahwcU'
})(MapContainer);
