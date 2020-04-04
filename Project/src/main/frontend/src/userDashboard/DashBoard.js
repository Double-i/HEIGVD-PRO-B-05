import * as React from "react";
import { Link } from 'react-router-dom'

class DashBoard extends React.Component {
    render() {
        return (
            <div>
                <button><Link to="/AddTools">Add Tools</Link></button>
            </div>
        );
    }
}

export default DashBoard;