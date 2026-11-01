export default function AccountStatusDropdown
({value,
onChange,
required = 
false}) {
    return (
    <select
    id = 
    "status"
    name = 
    "status"
    value = 
    {value || ""}
    onChange = 
    {onChange}
    required = 
    {required}> 
    <option value = 
    "">Select Account Status
    </option> 
    <option value = 
    "IN_USE">In Use
    </option> 
    <option value = 
    "NOT_IN_USE">
    Not In Use
    </option> 
    </select>
    );
}