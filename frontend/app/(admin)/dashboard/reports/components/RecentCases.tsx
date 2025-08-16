// components/RecentCases.tsx
import { Badge } from "@/components/ui/badge";

const cases = [
  {
    id: "C-1024",
    title: "Unauthorized Access Attempt",
    status: "Urgent",
    assignedTo: "Officer Smith",
    date: "2023-11-15"
  },
  {
    id: "C-1024",
    title: "Unauthorized Access Attempt",
    status: "Urgent",
    assignedTo: "Officer Smith",
    date: "2023-11-15"
  },
  {
    id: "C-1024",
    title: "Unauthorized Access Attempt",
    status: "Urgent",
    assignedTo: "Officer Smith",
    date: "2023-11-15"
  },
  // ... more cases
];

export default function RecentCases() {
  return (
    <div className="divide-y">
      {cases.map((caseItem) => (
        <div key={caseItem.id} className="p-4 hover:bg-gray-100 transition-colors mt-5">
          <div className="flex justify-between items-center">
            <div>
              <p className="font-medium">#{caseItem.id}</p>
              <p className="text-sm text-gray-600">{caseItem.title}</p>
            </div>
            <Badge variant={caseItem.status === "Urgent" ? "destructive" : "default"}>
              {caseItem.status}
            </Badge>
          </div>
          <div className="flex justify-between mt-2 text-sm text-gray-500">
            <span>Assigned to {caseItem.assignedTo}</span>
            <span>{caseItem.date}</span>
          </div>
        </div>
      ))}
    </div>
  );
}