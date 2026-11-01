import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import Button from "../components/Button";
import AccountTypeDropdown from "../components/dropdown/AccountTypeDropdown";
import AccountStatusDropdown from "../components/dropdown/AccountStatusDropdown";
import "../styles/Form.css";

//API Configuration
const API_URL = 
"http://localhost:8080/account_info";

//Create an empty account record
const saveEmptyRecord = 
() => ({
name: "",
type: "",
account_number: "",
status: "",
amount: ""
});

export default function AccountForm 
() {
    const navigate = 
    useNavigate();
    const {id} = 
    useParams();

    //Store account details
    const [account, 
    setAccount] = 
    useState(saveEmptyRecord);

    //Store original account details for reset
    const [originalAccount, 
    setOriginalAccount] = 
    useState(saveEmptyRecord);

    //Store loading & error status
    const [loading, 
    setLoading] = 
    useState(false);
    const [error, 
    setError] = 
    useState("");

    //Determine whether form is creating or updating
    const isUpdating = 
    Boolean(id);

    //Retrieve existing account when updating
    useEffect(() => {
        if (!id) {
            setAccount(saveEmptyRecord());
            setOriginalAccount(saveEmptyRecord());
            setError("");
            return;
        }

        const retrieveAccount = 
        async () => {
            setLoading(true);
            setError("");
            try {
                const response = 
                await axios
                .get(
                `${API_URL}/id/${id}`);
                setAccount(response
                .data);
                setOriginalAccount(response
                .data);
            } catch (err) {
                console
                .error("Error retrieving account:", 
                err);
                setError(
                err.response?.data?.message ||
                err.message ||
                "Unable to retrieve account record.");
            } finally {
                setLoading(false);
            }
        };

        retrieveAccount();

    }, [id]);

    //Handle input changes
    const onInputChange = 
    (event) => {
        const {name, 
        value} = 
        event
        .target;

        setAccount((previous) => ({
            ...previous,
            [name]: value
        }));

        setError("");
    };

    //Create or update account record
    const handleSave = 
    async (event) => {
        event
        .preventDefault();

        //Validate required fields
        if (!account.name?.trim() ||
            !account
            .type ||
            !account
            .account_number?.trim() ||
            !account
            .status ||
            account
            .amount === "" ||
            account
            .amount === null ||
            account
            .amount === undefined ||
            !Number
            .isFinite(Number(account
            .amount))
        ) {
            setError("Please complete all account fields with valid values.");
            return;
        }

        setLoading(true);
        setError("");
        try {
            //Prepare account data
            const accountData = {
            ...account,
            amount: Number(account
            .amount)
        };

            //Submit account record
            const response = 
            await axios
            .post(
            `${API_URL}/save`,
            accountData);

            console
            .log("Account saved successfully:", 
            response
            .data);

            //Navigate to Account Dashboard
            navigate("/account_info/retrieve");
        } catch (err) {
            console
            .error("Error saving account:", 
            err);

            setError(
            err.response?.data?.message ||
            err.message ||
            "Unable to save account record.");
        } finally {
            setLoading(false);
        }
    };

    //Reset account form
    const handleReset = 
    () => {
        setAccount({...originalAccount});
        setError("");
    };

    //Return to Account Dashboard
    const handleCancel = 
    () => {
        navigate("/account_info/retrieve");
    };

    return (
        <div className = 
        "container-centered">
            <div className = 
            "form-wrapper">
                {/* Form Heading */}
                <h1 className = 
                "form-page text-center">
                    {isUpdating
                    ? "Update Account Record"
                    : "New Account Record"}
                </h1>

                {/* Display error message */}
                {error && (
                    <div className = 
                    "alert alert-danger" 
                    role = "alert">
                        {error}
                    </div>
                )}

                <form onSubmit = 
                {handleSave}>
                    {/* Account Details */}
                    <div className = 
                    "field-group">
                        <h3>Account Details</h3>
                        <div className = 
                        "account-fields">
                            {/* Account Name */}
                            <div className = 
                            "mb-3">
                            <label htmlFor = 
                            "name">
                                Account Name
                            </label>

                            <input
                                type = 
                                "text"
                                id = 
                                "name"
                                name = 
                                "name"
                                value = 
                                {account.name || ""}
                                onChange = 
                                {onInputChange}
                                required/>

                            </div>

                            {/* Account Type */}
                            <div className = 
                            "mb-3">

                                <label htmlFor = 
                                "type">
                                    Account Type
                                </label>

                                <AccountTypeDropdown
                                value = 
                                {account
                                .type}
                                onChange = 
                                {onInputChange}
                                required/>
                            </div>

                            {/* Account Number */}
                            <div className = 
                            "mb-3">

                                <label htmlFor = 
                                "account_number">
                                    Account Number
                                </label>

                                <input
                                    type = 
                                    "text"
                                    id = 
                                    "account_number"
                                    name = 
                                    "account_number"
                                    value = 
                                    {account.account_number || ""}
                                    onChange = 
                                    {onInputChange}
                                    required/>
                            </div>

                            {/* Account Status */}
                            <div className = 
                            "mb-3">

                                <label htmlFor = 
                                "status">
                                    Account Status
                                </label>

                                <AccountStatusDropdown
                                value = 
                                {account.status}
                                onChange = 
                                {onInputChange}
                                required/>
                            </div>

                            {/* Account Amount */}
                            <div className = 
                            "mb-3">
                                <label htmlFor = 
                                "amount">
                                    Amount
                                </label>

                                <input
                                    type = 
                                    "number"
                                    id = 
                                    "amount"
                                    name = 
                                    "amount"
                                    value = 
                                    {account.amount ?? ""}
                                    onChange = 
                                    {onInputChange}
                                    step = 
                                    "0.01"
                                    required
                                />
                            </div>
                        </div>

                    </div>

                    {/* Form Buttons */}
                    <div className = 
                    "button-wrapper">
                        <Button
                            type = 
                            "submit"
                            disabled = 
                            {loading}>
                            {loading
                            ? "Saving..."
                            : isUpdating ? 
                            "Update" : "Save"}
                        </Button>

                        <Button
                            type = 
                            "button"
                            onClick = 
                            {handleReset}
                            disabled = 
                            {loading}>
                            Reset
                        </Button>

                        <Button
                            type = 
                            "button"
                            onClick = 
                            {handleCancel}
                            disabled = 
                            {loading}>
                            Cancel
                        </Button>
                    </div>
                </form>
            </div>
        </div>
    );
}