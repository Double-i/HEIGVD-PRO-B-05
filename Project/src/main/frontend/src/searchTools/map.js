import { Map, GoogleApiWrapper, Marker, InfoWindow} from 'google-maps-react';
import React from "react"
import {sendEzApiRequest} from "../common/ApiHelper";
import { usePosition } from 'use-position';
import MarkerClusterer from 'node-js-marker-clusterer';
import {SessionContext} from '../common/SessionHelper'


const mapStyles = {
    width :  '80%'
}

/**
 * The component containing the google-map
 */
class MapContainer extends React.Component {
    static contextType = SessionContext
    SEARCH_URI = '/objects'
    TAGS_URI = '/tags'

    /**
     * Constructor
     * @param props Should contain a list of tools
     */
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
            selectedTool: {}
        }
    }

    /**
     * Called whenever a marker on the map is clicked
     * @param props Should contain the selected tool's name
     * @param marker the marker that was clicked
     * @param e Event (unused)
     */
    onMarkerClick = (props, marker, e) => {
        console.log("Clicked on marker !");
        this.setState({
            selectedTool: this.props.tools.find(tool => tool.name === props.name),
            activeMarker: marker,
            showingInfoWindow: true
        });
        console.log(this.state.selectedTool);
    }

    /**
     * Called whenever the map is clicked
     * @param props
     */
    onMapClicked = (props) => {
        if (this.state.showingInfoWindow) {
            this.setState({
                showingInfoWindow: false,
                activeMarker: null
            })
        }
    };

    /**
     * gets all the tools With the address of the current selected Tool
     * (aka position of the clicked marker)
     * @param selectedTool the currently selected tool (corresponding to the clicked marker)
     * @returns multiple divs, each containing info on a tool
     */
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

    /**
     * gets the infoWindow corresponding to the selected tool
     * @returns {*}
     */
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

    /**
     * gets a marker for each tool passed as prop to the component
     * @returns all markers corresponding to the tools
     */
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
                 zoom={8}
                 style={mapStyles}
                 initialCenter={{ lat: this.context.userInfo.address.lat, lng: this.context.userInfo.address.lng}}
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
