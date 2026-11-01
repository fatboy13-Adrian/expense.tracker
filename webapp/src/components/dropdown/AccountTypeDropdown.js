export default function AccountTypeDropdown
({value,
onChange,
required = 
false}) {
    return (
    <select
    id = 
    "type"
    name = 
    "type"
    value = 
    {value || ""}
    onChange = 
    {onChange}
    required = 
    {required}> 
    <option value = 
    "">
    Select Account Type
    </option> 
    <option value = 
    "CHECKING">
    Checking
    </option> 
    <option value = 
    "SAVINGS">
    Savings
    </option> 
    <option value = 
    "SRS">
    SRS
    </option> 
    <option value = 
    "INSURANCE">
    Insurance
    </option> 
    <option value = 
    "NEEDS">
    Needs
    </option> 
    <option value = 
    "WANTS">
    Wants
    </option> 
    </select>
    );
}