import { Map, GoogleApiWrapper, Marker, InfoWindow} from 'google-maps-react';
import React from "react"
import {sendEzApiRequest} from "../common/ApiHelper";
import { usePosition } from 'use-position';
import MarkerClusterer from 'node-js-marker-clusterer';

const mapStyles = {
    width :  '80%'
}

/*export function getUserPos(Component) {
    return function WrappedComponent(props) {
        const position = usePosition();
        return <Component {...props} position={usePosition()} />;
    }
}*/

class MapContainer extends React.Component {

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
            initialPos: {lat:40, lng:40}
        }


    }

    componentDidMount() {
        if (!navigator.geolocation) {
            alert("Geolocaton is not supported by your browser");
        } else
        {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const initialPosition = JSON.stringify(position);
                    this.setState({ initialPosition });
                    console.log(this.state.initialPosition);
                },
                (error) => alert("Geolocation : "+error.message),
                { enableHighAccuracy: true, timeout: 20000, maximumAge: 1000 }
            );
        }

    }

    onMarkerClick = (props, marker, e) => {
        console.log("Clicked on marker !");
        this.setState({
            selectedTool: this.props.tools.find(tool => tool.name === props.name),
            activeMarker: marker,
            showingInfoWindow: true
        });
        console.log(this.state.selectedTool);
    }

    onMapClicked = (props) => {
        if (this.state.showingInfoWindow) {
            this.setState({
                showingInfoWindow: false,
                activeMarker: null
            })
        }
    };

    getToolsFromMarker(selectedTool)
    {
        console.log(selectedTool);
        if(selectedTool.owner !== undefined)
        {
            let tools = this.props.tools.filter(tool => {
                console.log(tool);
                console.log(selectedTool);
                return tool.owner.address.lat == selectedTool.owner.address.lat &&
                    tool.owner.address.lng == selectedTool.owner.address.lng
            })
            console.log(tools);
            return tools.map(tool => {
                return <div>
                    <h2>{tool === undefined ? "" : tool.name}</h2>
                    <p>
                        {tool === undefined ? "" : tool.description}
                    </p>
                    <a href={"/toolDetails/"+tool.id}>Details</a>
                </div>
            })
        }
    }

    getInfoWindow()
    {
        console.log("Reloading InfoWindow")
        return <InfoWindow
        marker = {this.state.activeMarker}
        visible = {this.state.showingInfoWindow}>
        <div>
            {this.getToolsFromMarker(this.state.selectedTool)}
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
                 zoom={5}
                 style={mapStyles}
                 initialCenter={{ lat: this.state.initialPos.lat, lng: this.state.initialPos.lng}}
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
