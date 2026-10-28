import {useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import Button from "./components/Button";
import TextField from "./components/Textfield";
import InputDate from "./components/inputs/InputDate";
import "./styles/Form.css";

const API_URL = 
"http://localhost:8080/daily";

const getTodayDate = 
() => {
    const today = 
    new Date();
    const localDate = 
    new Date(today
    .getTime() - today
    .getTimezoneOffset() 
    * 60000);

    return localDate
    .toISOString()
    .split("T")[0];
};

const saveEmptyRecord = 
(date = 
getTodayDate()) => 
({
    date,
    income: "",
    debt: "",
    emergency: "",
    srs: "",
    ssb: "",
    cdac: "",
    electricity: "",
    internet: "",
    insurances: "",
    mobile: "",
    mortgage: "",
    tax: "",
    food: "",
    groceries: "",
    haircut: "",
    household: "",
    medical: "",
    parents: "",
    transport: "",
    eating_out: "",
    holiday: "",
    recreational: "",
    shopping: "",
    sports: "",
    tech: "",
    tithes: "",
    wake: "",
    wedding: "",
});

const fieldGroups = [
    {
        title: "Income & Debt",
        fields: [
            {name: "income", 
            label: "Income", 
            type: "number"},
            {name: "debt", 
            label: "Debt", 
            type: "number"},
        ],
    },
    {
        title: "Savings",
        fields: [
            {name: "emergency", 
            label: "Emergency", 
            type: "number"},
            {name: "srs", 
            label: "SRS", 
            type: "number"},
            {name: "ssb", 
            label: "SSB", 
            type: "number"},
        ],
    },
    {
        title: "Bills",
        fields: [
            {name: "cdac", 
            label: "Cdac", 
            type: "number"},
            {name: "electricity", 
            label: "Electricity", 
            type: "number"},
            {name: "internet", 
            label: "Internet", 
            type: "number"},
            {name: "insurances", 
            label: "Insurances", 
            type: "number"},
            {name: "mobile", 
            label: "Mobile", 
            type: "number"},
            {name: "mortgage", 
            label: "Mortgage",
            type: "number"},
            {name: "tax", 
            label: "Tax", 
            type: "number"},
        ],
    },
    {
        title: "Needs",
        fields: [
            {name: "food", 
            label: "Food", 
            type: "number"},
            {name: "groceries", 
            label: "Groceries", 
            type: "number"},
            {name: "haircut", 
            label: "Haircut", 
            type: "number"},
            {name: "household", 
            label: "Household", 
            type: "number"},
            {name: "medical", 
            label: "Medical", 
            type: "number"},
            {name: "parents", 
            label: "Parents", 
            type: "number"},
            {name: "transport", 
            label: "Transport", 
            type: "number"},
        ],
    },
    {
        title: "Wants",
        fields: [
            {name: "eating_out", 
            label: "Eating Out", 
            type: "number"},
            {name: "holiday", 
            label: "Holiday", 
            type: "number"},
            {name: "recreational", 
            label: "Recreational", 
            type: "number"},
            {name: "shopping", 
            label: "Shopping", 
            type: "number"},
            {name: "sports", 
            label: "Sports", 
            type: "number"},
            {name: "tech", 
            label: "Tech", 
            type: "number"},
        ],
    },
    {
        title: "Others",
        fields: [
            {name: "tithes", 
            label: "Tithes", 
            type: "number"},
            {name: "wake", 
            label: "Wake", 
            type: "number"},
            {name: "wedding", 
            label: "Wedding", 
            type: "number"},
        ],
    },
];

const numericalFieldNames = 
fieldGroups
.flatMap((group) =>
group
.fields
.map((field) => 
field
.name));

const toDailyFormValues = 
(data, date) => {
    const values = 
    saveEmptyRecord(date);
    numericalFieldNames
    .forEach((name) => {
        const value = 
        data?.[name];
        values[name] =
        value === null || 
        value === undefined
        ? ""
        : String(value);
    });

    return {
        ...values,
        date,
    };
};

export default function DailyForm() {
    const navigate = 
    useNavigate();
    const [daily, 
    setDaily] = 
    useState(() => 
    saveEmptyRecord());
    const [recordExists, 
    setRecordExists] = 
    useState(false);
    const [loading, 
    setLoading] = 
    useState(false);
    const [error, 
    setError] = 
    useState("");

    const onInputChange = 
    (event) => {
        const {name, 
        value} = 
        event.target;

        if (name === "date") {
            setDaily(saveEmptyRecord(value));
            setRecordExists(false);
            setError("");
            return;
        }

        setDaily((previous) => ({
        ...previous,
        [name]: value,}));
    };

    const handleSearch = 
    async () => {
        if (!daily.date) {
            setError("Select a date.");
            return;
        }

        setLoading(true);
        setError("");

        try {
            const response = 
            await axios
            .get(
            `${API_URL}/date/${daily.date}`);

            const data = 
            Array
            .isArray(response
            .data)
            ? response.data[0]
            : response.data;

            if (!data) {
                setDaily(saveEmptyRecord(daily
                .date));
                setRecordExists(false);
                return;
            }

            setDaily(toDailyFormValues(data, 
            daily
            .date));
            setRecordExists(true);
        } catch (err) {
            if (err.response?.status === 404) {
                setDaily(saveEmptyRecord(daily.date));
                setRecordExists(false);
            } else {
                setError(
                err.response?.data?.message ||
                "Unable to retrieve record.");
            }
        } finally {
            setLoading(false);
        }
    };

    const onSubmit = 
    async (event) => {
        event
        .preventDefault();

        if (!daily.date) {
            setError("Select a date.");
            return;
        }

        setLoading(true);
        setError("");

        const payload = {
            date: daily
            .date,
            ...numericalFieldNames
            .reduce((acc, 
            name) => {
                const value = 
                daily[name];
                acc[name] =
                value === "" ||
                value === null ||
                value === undefined
                ? 0
                : Number(value);

                return acc;
            }, {}),
        };

        try {
            if (recordExists) {
                await axios
                .put(
                `${API_URL}/date/${daily.date}`,
                payload);
            } else {
                await axios
                .post(
                `${API_URL}/save`,
                payload);
            }

            navigate("/daily/retrieve", {
                state: {refresh: 
                true},
            });
        } catch (err) {
            if (!recordExists && 
            err.response?.status === 409) {
                setError(
                "Record already exists. Retrieving record for editing.");
            } else {
                setError(
                    err.response?.data?.message ||
                    err.message ||
                    `Failed to ${
                    recordExists ? 
                    "edit" 
                    : "save"
                    } daily record.`
                );
            }
        } finally {
            setLoading(false);
        }
    };

    const handleReset = 
    () => {
        setDaily(saveEmptyRecord(daily
        .date));
        setRecordExists(false);
        setError("");
    };

    return (
        <div className = 
        "container-centered">
            <div className = 
            "form-wrapper">
                <h2 className = 
                "form-page text-center">
                    {recordExists
                    ? "Edit Daily Expense Record"
                    : "Save Daily Expense Record"}
                </h2>

                {error && (
                    <div className = 
                    "alert alert-danger">
                        {error}
                    </div>
                )}

                <div className = 
                "date-retrieve-row">
                    <div className = 
                    "date-input-wrapper">
                        <InputDate
                            name = 
                            "date"
                            value = 
                            {daily.date}
                            onChange = 
                            {onInputChange}
                            disabled = 
                            {loading}/>
                    </div>

                    <Button
                        type = 
                        "button"
                        onClick = 
                        {handleSearch}
                        disabled = 
                        {loading || 
                        !daily.date}
                    >
                        {loading ? 
                        "Please wait..." 
                        : "Retrieve"}
                    </Button>

                </div>

                <form onSubmit = 
                {onSubmit} noValidate>
                    <div className = 
                    "field-group-container">
                        {fieldGroups
                        .map((group) => (
                            <section
                                key = 
                                {group.title}
                                className = 
                                "field-group">
                                <h3>{group.title}</h3>

                                <div className = 
                                "field-group-fields">
                                    {group.fields
                                    .map((field) => (
                                        <TextField
                                            key = 
                                            {field.name}
                                            name = 
                                            {field.name}
                                            label = 
                                            {field.label}
                                            type = 
                                            {field.type}
                                            value = 
                                            {daily[field.name] 
                                            ?? ""}
                                            onChange = 
                                            {onInputChange}
                                            disabled = 
                                            {loading}
                                            className = 
                                            "mb-3 full-width"/>
                                    ))}
                                </div>
                            </section>
                        ))}

                    </div>

                    <div className = 
                    "button-wrapper">
                        <Button
                            type = 
                            "button"
                            onClick = 
                            {() => 
                            navigate("/daily/retrieve")}
                            disabled = 
                            {loading}>
                            View
                        </Button>

                        <Button
                            type = 
                            "button"
                            onClick = 
                            {handleReset}
                            disabled = 
                            {loading}>
                            Discard
                        </Button>

                        <Button
                            type = 
                            "submit"
                            disabled = 
                            {loading || 
                            !daily.date}>
                            {loading
                            ? "Saving..."
                            : recordExists
                            ? "Edit"
                            : "Save"}
                        </Button>

                        <Button
                            type = "button"
                            onClick = 
                            {() => 
                            navigate("/daily")}
                            disabled = 
                            {loading}>
                            Daily
                        </Button>

                        <Button
                            type = "button"
                            onClick = 
                            {() => 
                            navigate("/home")}
                            disabled = 
                            {loading}>
                            Home
                        </Button>
                    </div>
                </form>
            </div>
        </div>
    );
}