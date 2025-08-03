'use client';

import { useParams } from 'next/navigation';
import Link from 'next/link';
import { Button } from '@/components/ui/button';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import Badge from '@/components/ui/badge';
import { PlusIcon } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';

export default function CaseListPage() {
  const { role } = useParams();

  // Sample mock data
  const cases = [
    {
      id: 'C001',
      title: 'Burglary at 5th Street',
      status: 'Open',
      priority: 'High',
      assignedTo: role === 'investigator' ? 'You' : 'Detective A',
      createdAt: '2023-06-15',
      updatedAt: '2023-06-20',
    },
    {
      id: 'C002',
      title: 'Robbery near Central Park',
      status: 'In Progress',
      priority: 'Medium',
      assignedTo: role === 'investigator' ? 'You' : 'Detective B',
      createdAt: '2023-06-10',
      updatedAt: '2023-06-18',
    }
  ];

  const statusVariant = (status: string) => {
    switch (status) {
      case 'Open': return 'destructive';
      case 'In Progress': return 'warning';
      case 'Closed': return 'success';
      default: return 'default';
    }
  };

  const priorityVariant = (priority: string) => {
    switch (priority) {
      case 'High': return 'destructive';
      case 'Medium': return 'warning';
      case 'Low': return 'success';
      default: return 'default';
    }
  };

  return (
    <div className="p-6 space-y-6 max-w-8xl mx-auto">
      {/* Breadcrumb */}
      <nav className="flex items-center space-x-2 text-sm">
        <Link href="/" className="text-gray-500 hover:text-primary hover:underline">
          Home
        </Link>
        <span className="text-gray-400">/</span>
        <Link href={`/${role}`} className="text-gray-500 hover:text-primary hover:underline capitalize">
          {role}
        </Link>
        <span className="text-gray-400">/</span>
        <span className="text-primary font-medium">Cases</span>
      </nav>

      {/* Page Header */}
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold text-gray-800">Case Management</h1>
          <p className="text-sm text-gray-500 mt-1">
            {role === 'investigator' 
              ? 'Your assigned cases' 
              : 'All active cases'}
          </p>
        </div>
        <div className="flex gap-3">
          <Button className="rounded-lg gap-2 border-gray-300" size="sm" 
                      variant="outline" >
            <PlusIcon className="h-4 w-4" />
            New Case
          </Button>
          {role === 'supervisor' && (
            <Button variant="outline" className="rounded-lg">
              Assign Cases
            </Button>
          )}
        </div>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        {[
          { title: 'Total Cases', value: cases.length },
          { 
            title: 'Open Cases', 
            value: cases.filter(c => c.status === 'Open').length 
          },
          { 
            title: 'Your Cases', 
            value: cases.filter(c => c.assignedTo === 'You').length 
          }
        ].map((stat, index) => (
          <Card key={index} className="rounded-xl border border-gray-200">
            <CardHeader className="pb-2 px-5 pt-5">
              <CardTitle className="text-sm font-medium text-gray-600">
                {stat.title}
              </CardTitle>
            </CardHeader>
            <CardContent className="px-5 pb-5">
              <div className="text-2xl font-bold text-gray-800">
                {stat.value}
              </div>
            </CardContent>
          </Card>
        ))}
      </div>

      {/* Cases Table */}
      <Card className="rounded-xl border border-gray-200 overflow-hidden">
        <CardHeader className="border-b p-5 bg-gray-50">
          <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
            <CardTitle className="text-lg font-semibold text-gray-800">
              Case Records
            </CardTitle>
            <div className="flex items-center gap-2">
              <Button 
                variant="outline" 
                size="sm" 
                className="rounded-lg border-gray-300"
              >
                Filter
              </Button>
              <Button 
                variant="outline" 
                size="sm" 
                className="rounded-lg border-gray-300"
              >
                Sort
              </Button>
            </div>
          </div>
        </CardHeader>
        <CardContent className="p-0">
          <Table>
            <TableHeader>
              <TableRow className="hover:bg-transparent">
                {[
                  'Case ID',
                  'Title',
                  'Status',
                  'Priority',
                  'Assigned To',
                  'Last Updated',
                  'Actions'
                ].map((header, index) => (
                  <TableHead 
                    key={index} 
                    className={`
                      px-5 py-3 text-gray-700 font-medium
                      ${index === 0 ? 'w-[100px]' : ''}
                      ${index === 6 ? 'text-right' : ''}
                    `}
                  >
                    {header}
                  </TableHead>
                ))}
              </TableRow>
            </TableHeader>
            <TableBody>
              {cases.map((c) => (
                <TableRow 
                  key={c.id} 
                  className="hover:bg-gray-50/50 border-t border-gray-100"
                >
                  <TableCell className="px-5 py-4 font-medium text-gray-800">
                    {c.id}
                  </TableCell>
                  <TableCell className="px-5 py-4">
                    <Link 
                      href={`/${role}/cases/${c.id}`} 
                      className="hover:text-primary hover:underline"
                    >
                      {c.title}
                    </Link>
                  </TableCell>
                  <TableCell className="px-5 py-4">
                    <Badge 
                      variant={statusVariant(c.status)} 
                      className="rounded-md"
                    >
                      {c.status}
                    </Badge>
                  </TableCell>
                  <TableCell className="px-5 py-4">
                    <Badge 
                      variant={priorityVariant(c.priority)} 
                      className="rounded-md"
                    >
                      {c.priority}
                    </Badge>
                  </TableCell>
                  <TableCell className="px-5 py-4 text-gray-600">
                    {c.assignedTo}
                  </TableCell>
                  <TableCell className="px-5 py-4 text-gray-600">
                    {c.updatedAt}
                  </TableCell>
                  <TableCell className="px-5 py-4 text-right space-x-2">
                    <Button 
                      size="sm" 
                      variant="outline" 
                      className="rounded-lg border-gray-300"
                    >
                      View
                    </Button>
                    {(role === 'investigator' || role === 'supervisor') && (
                      <Button size="sm" className="rounded-lg border-gray-300" 
                      variant="outline">
                        Edit
                      </Button>
                    )}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </div>
  );
}