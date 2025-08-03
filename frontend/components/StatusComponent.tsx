interface StatusComponentProps {
  status: string; // Define the status as a string prop
}

export function StatusComponent({ status }: StatusComponentProps) {
  // Ensure status is a string, default to "Unknown" if not provided
  const displayStatus = typeof status === "string" ? status : "Unknown";

  // Define status styles
  const statusStyles: { [key: string]: string } = {
    Pending: "bg-yellow-100 text-yellow-800",
    Completed: "bg-green-100 text-green-800",
    Rejected: "bg-red-100 text-red-800",
    Unknown: "bg-gray-100 text-gray-800",
  };

  return (
    <span
      className={`inline-block px-3 py-1 rounded-full text-xs font-medium ${statusStyles[displayStatus] || statusStyles.Unknown
        }`}
    >
      {displayStatus}
    </span>
  );
}