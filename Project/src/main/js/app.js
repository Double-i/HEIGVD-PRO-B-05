'use strict';

// tag::vars[]
const React = require('react'); // <1>
const ReactDOM = require('react-dom'); // <2>
// end::vars[]

// tag::app[]
class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {employees: [], newEmployee:""};
    }

    componentDidMount() {


    }
    _addEmployee(){
        if(this.state.newEmployee !== ""){
            const employees = this.state.employees.concat(this.state.newEmployee);
            this.setState( {
                employees,
                newEmployee: '',
            })
        }
    }
    _handleChange(newVal){
        this.setState({newEmployee: newVal});
    }


    render() {
        return (
            <div>Wonderful but not updating... I dont know why
                <ul>
                    {this.state.employees.map((number) =>
                        <li>{number}</li>
                    )}
                </ul>
                <input value={this.state.newEmployee} onChange={(event => this._handleChange( event.target.value))} />
                <button onClick={()=> {this._addEmployee()}}> Ajouter </button>
            </div>

        )
    }
}
ReactDOM.render(
    <App />,
    document.getElementById('react')
)