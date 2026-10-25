import "../styles/Table.css";   

export default function DailyAction 
({daily, onUpdate, disabled}) {
    return (
        <div className = "action-buttons">
            <button
            onClick={() => 
            onUpdate(daily)}
            disabled = 
            {disabled}>
                Update
            </button>
        </div>
    );
}