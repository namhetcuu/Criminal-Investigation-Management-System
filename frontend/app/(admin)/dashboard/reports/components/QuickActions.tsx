// components/QuickActions.tsx
import { Button } from "@/components/ui/button";
import { Plus, Download, Settings, UserPlus } from "lucide-react";

export default function QuickActions() {
  return (
    <div className="grid grid-cols-2 gap-3 mt-5">
      <Button variant="outline" className="h-15 flex-col gap-2">
        <Plus className="h-5 w-5" />
        <span>New Case</span>
      </Button>
      <Button variant="outline" className="h-15 flex-col gap-2">
        <Download className="h-5 w-5" />
        <span>Generate Report</span>
      </Button>
      <Button variant="outline" className="h-15 flex-col gap-2">
        <UserPlus className="h-5 w-5" />
        <span>Add User</span>
      </Button>
      <Button variant="outline" className="h-15 flex-col gap-2">
        <Settings className="h-5 w-5" />
        <span>Settings</span>
      </Button>
    </div>
  );
}