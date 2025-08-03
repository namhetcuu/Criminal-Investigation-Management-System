'use client';

import { useParams } from 'next/navigation';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { PlusIcon, GanttChartIcon, CalendarIcon, FileTextIcon } from 'lucide-react';
import { Badge } from '@/components/ui/badge';

export default function InvestigationPlansPage() {
  const { role } = useParams();

  // Mock data for investigation plans
  const plans = [
    {
      id: 'IP-001',
      title: 'Burglary Case Investigation',
      status: 'Active',
      priority: 'High',
      assignedTo: role === 'investigator' ? 'You' : 'Detective Smith',
      createdDate: '2023-06-10',
      dueDate: '2023-06-30',
      tasks: [
        'Interview key witnesses',
        'Collect forensic evidence',
        'Review CCTV footage'
      ]
    },
    {
      id: 'IP-002',
      title: 'Fraud Case Follow-up',
      status: 'Pending',
      priority: 'Medium',
      assignedTo: 'Detective Johnson',
      createdDate: '2023-06-15',
      dueDate: '2023-07-10',
      tasks: [
        'Contact bank for transaction records',
        'Interview suspect',
        'Prepare court documents'
      ]
    },
     {
      id: 'IP-003',
      title: 'Cold Case Review',
      status: 'On Hold',
      priority: 'Low',
      assignedTo: 'Detective Williams',
      createdDate: '2023-05-20',
      dueDate: '2023-08-15',
      tasks: [
        'Re-examine evidence',
        'Re-interview witnesses',
        'Consult with forensic team'
      ]
    }
  ];

  const statusVariant = (status: string) => {
    switch (status) {
      case 'Active': return 'default';
      case 'Pending': return 'warning';
      case 'On Hold': return 'destructive';
      case 'Completed': return 'success';
      default: return 'outline';
    }
  };

  const priorityVariant = (priority: string) => {
    switch (priority) {
      case 'High': return 'destructive';
      case 'Medium': return 'warning';
      case 'Low': return 'success';
      default: return 'outline';
    }
  };

  return (
    <div className="p-6 space-y-6">
      {/* Page Header */}
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold text-gray-800">Investigation Plans</h1>
          <p className="text-sm text-gray-500 mt-1">
            {role === 'investigator' 
              ? 'Your assigned investigation plans' 
              : 'All active investigation plans'}
          </p>
        </div>
        
        <div className="flex gap-2">
          <Button className="rounded-lg">
            <PlusIcon className="mr-2 h-4 w-4" />
            New Plan
          </Button>
          {role === 'supervisor' && (
            <Button variant="outline" className="rounded-lg">
              <GanttChartIcon className="mr-2 h-4 w-4" />
              Assign Plans
            </Button>
          )}
        </div>
      </div>

      {/* Plans Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
        {plans.map((plan) => (
          <Card key={plan.id} className="rounded-xl shadow-sm hover:shadow-md transition-all border border-gray-100 overflow-hidden">
            <CardHeader className="pb-3 px-5 pt-5 bg-gray-50 border-b">
              <div className="flex justify-between items-start space-y-2">
                <CardTitle className="text-lg font-semibold text-gray-800">
                  {plan.title}
                </CardTitle>
                <div className="flex gap-2">
                  <Badge 
                    variant={statusVariant(plan.status)} 
                    className="rounded-md"
                  >
                    {plan.status}
                  </Badge>
                  <Badge 
                    variant={priorityVariant(plan.priority)} 
                    className="rounded-md"
                  >
                    {plan.priority}
                  </Badge>
                </div>
              </div>
            </CardHeader>
            <CardContent className="p-5">
              <div className="space-y-4">
                {/* Date and Assignee */}
                <div className="flex justify-between text-sm">
                  <div className="flex items-center text-gray-600">
                    <CalendarIcon className="mr-2 h-4 w-4 text-gray-400" />
                    <span>Due: {plan.dueDate}</span>
                  </div>
                  <div className="text-right text-gray-600">
                    <span className="font-medium text-gray-700">{plan.assignedTo}</span>
                  </div>
                </div>

                {/* Tasks List */}
                <div className="space-y-3">
                  <h3 className="font-medium flex items-center text-gray-700">
                    <FileTextIcon className="mr-2 h-4 w-4 text-gray-400" />
                    Tasks:
                  </h3>
                  <ul className="list-disc pl-5 space-y-2 text-sm text-gray-600">
                    {plan.tasks.map((task, index) => (
                      <li key={index} className="leading-snug">{task}</li>
                    ))}
                  </ul>
                </div>

                {/* Action Buttons */}
                <div className="flex justify-end gap-3 pt-3">
                  <Button 
                    variant="outline" 
                    size="sm" 
                    className="rounded-lg border-gray-300"
                  >
                    View Details
                  </Button>
                  {(role === 'investigator' || role === 'supervisor') && (
                    <Button 
                      size="sm" 
                      className="rounded-lg"
                    >
                      Edit
                    </Button>
                  )}
                </div>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}