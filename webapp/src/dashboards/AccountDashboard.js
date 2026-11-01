import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import AccountTable from "../components/tables/AccountTable";
import "../styles/Dashboard.css";

//API Configuration
const API_URL = 
"http://localhost:8080/account_info";

export default function AccountDashboard 
() {
    //Store account records
    const [accounts, 
    setAccounts] = 
    useState([]);

    //Store errors and loading status
    const [error, 
    setError] = 
    useState("");
    const [loading, 
    setLoading] = 
    useState(true);
    const navigate = 
    useNavigate();

    //Fetch account records
    useEffect(() => {
        let isMounted = 
        true;

        const fetchAccounts = 
        async () => {
            setLoading(true);
            setError("");

            try {
                console
                .log("Fetching accounts from:", 
                API_URL);

                const response = 
                await axios
                .get(API_URL);

                console
                .log("Account API response:", 
                response
                .data);

                if (isMounted) {
                    setAccounts(response
                    .data);
                    setLoading(false);
                }
            } catch (error) {
                console
                .error(
                "Error fetching account records:",
                error);

                if (isMounted) {
                    setError("Unable to fetch account records.");
                    setLoading(false);
                }
            }
        };

        fetchAccounts();

        return () => {
            isMounted = 
            false;
        };
    }, []);

    return (
        <div className = 
        "dashboard-container">
            <h1 className = 
            "dashboard-title">
                Account Dashboard
            </h1>

            {loading ? (
                <div>Loading...</div>
            ) : error ? (
                <div
                    className = 
                    "error-message"
                    role = 
                    "alert">
                    {error}
                </div>
            ) : (
                <>
                    <AccountTable 
                    accounts = 
                    {accounts}
                    setAccounts =
                    {setAccounts}/>

                    <div className = 
                    "buttons-center">
                        <button
                            type = 
                            "button"
                            className = 
                            "custom-btn"
                            onClick = 
                            {() => 
                            navigate("/home")}>
                            Home
                        </button>

                        <button
                            type = 
                            "button"
                            className = 
                            "custom-btn"
                            onClick = 
                            {() => 
                            navigate("/account_info/form")}>
                            New / Edit
                        </button>


                        <button
                            type = 
                            "button"
                            className = 
                            "custom-btn"
                            onClick = 
                            {() => 
                            navigate("/yearly/retrieve")}>
                            Yearly
                        </button>
                    </div>
                </>
            )}
        </div>
    );
}