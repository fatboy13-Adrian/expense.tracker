import {useNavigate} from "react-router-dom";
import "../../styles/Table.css";
import axios from "axios";

//API Configuration
const API_URL = 
"http://localhost:8080/account_info";

function cleanText 
(text) {
    if (!text) 
    return "-";
    const trimmed = 
    text
    .toString()
    .trim();
    return trimmed.length === 0 ? "-" : trimmed;
}

function formatMoney 
(value) {
    if (value === null || 
    value === undefined || 
    value === "") 
    return "-";
    return `$${Number(value)
    .toFixed(2)}`;
}

export default function AccountTable 
({accounts = [], 
setAccounts}) {
    const navigate = 
    useNavigate();

    //Delete an account using its ID
    const handleDelete = 
    async (id) => {
        if (!window
        .confirm("Are you sure you want to delete this account?")) 
        return;
        
        try {
            await axios
            .delete(`${API_URL}/id/${id}`);

            //Remove deleted account from table
            setAccounts((previous) =>
            previous
            .filter((account) => 
            account.id !== 
            id));
        } catch (err) {
            console
            .error("Error deleting account:", 
            err);
            alert("Unable to delete account.");
        }
    };

    return (
        <div className = 
        "allTables-table-wrapper">
            <table className = 
            "allTables-table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Account No.</th>
                        <th>Status</th>
                        <th>Amount</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>
                    {accounts
                    .map((account) => (
                        <tr key = 
                        {account
                        .id}>
                            <td>{cleanText(account
                            .name)}</td>
                            <td>{cleanText(account
                            .type)}</td>
                            <td>{cleanText(account
                            .account_number)}</td>
                            <td>{cleanText(account
                            .status)}</td>
                            <td>{formatMoney(account
                            .amount)}</td>

                            {/* Actions column */}
                            <td className = 
                            "actions-cell">
                                <div className = 
                                "account-action-buttons">
                                    <button
                                    type = "button"
                                    onClick={() =>
                                    navigate(
                                    `/account_info/form/${account
                                    .id}`)}>
                                        Edit
                                    </button>

                                    <button
                                    type = 
                                    "button"
                                    onClick = 
                                    {() =>
                                    handleDelete(account
                                    .id)}>
                                        Delete
                                    </button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}