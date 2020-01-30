import React from 'react';
import Cookies from 'universal-cookie';
import './CSS/GameComponent.css';

class GameComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            message: "Welcome!",
            rollEnabled: true,
        }
    }

    /*
    * Checks if user has previous unique identifier (name) stored in cookies.
    * If not, request new unique identifier (name). Then save name to cookies.
    * If user has cookie with unique identifier. Request current user score from backend.
    */

    async componentDidMount() {

        console.log(window.location.href);

        let cookies = new Cookies("MMVincitButtonGameCookie");
        //cookies.remove('mmbgcookie');     //remove cookies for local testing

        if (cookies.get('mmbgcookie')) {
            this.setState({name: cookies.get('mmbgcookie')});
            fetch("credits/" +cookies.get('mmbgcookie')).then(response => response.json()).then(response => this.setState({credits : response.credits, clicks : response.clicksToPrize}));
        }
        else {
            var userData = await fetch("/create-user").then(response => response.json());
            cookies.set('mmbgcookie', userData.name, { path: '/', maxAge: 604800 });
            this.setState({name: userData.name});
            this.setState({credits: userData.credits, clicks: userData.clicksToPrize});
        }
    }

    /*
    * Roll button pressed function. 
    * This function is called when user presses roll-button.
    * Gives unique identifier (name) to backend and receive data (credits,message, clicks) based on that identifier.
    */

    buttonRoll = () => {
        this.setState({rollEnabled: false});
        fetch("/roll/" +this.state.name).then(response => response.json()).then(response => this.setState({credits : response.credits, clicks : response.clicksToPrize, message: response.message, rollEnabled: true}));
    }

    /*
    * Render method.
    * If credits = 0, show reset dialog.
    */

    render() {
        return (
            <div>
                <h1 className="messageText">{this.state.message}</h1>
                <h2>You Have {this.state.credits} Credits</h2>
                {this.state.clicks === 0 ? <h2>Roll to see how far the prize is...</h2> : <h2>Prize is just {this.state.clicks} clicks away...</h2>}
                <button className="button" onClick={this.buttonRoll} disabled={this.state.credits <= 0 || !this.state.rollEnabled ? true : false}>ROLL</button>
                {this.state.credits === 0 && <div>
                    <p>You are out of credits. Press button to reset</p> 
                    <button className="button" onClick={this.buttonRoll}>Reset</button>
                    </div>}
            </div>
        )
    }
}

export default GameComponent;