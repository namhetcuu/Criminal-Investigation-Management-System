'use client';

import { useParams } from 'next/navigation';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { PlusIcon, SearchIcon, FilterIcon, PhoneIcon, MailIcon } from 'lucide-react';
import { Input } from '@/components/ui/input';
import Badge from '@/components/ui/badge';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import Link from 'next/link';

export default function VictimsPage() {
  const { role } = useParams();

  // Sample victim data
  const victims = [
    {
      id: 'V-001',
      name: 'Jane Doe',
      age: 34,
      incident: 'Assault',
      contact: '0123456789',
      email: 'jane.doe@example.com',
      status: 'Needs Support',
      priority: 'High',
      lastContact: '2023-06-15'
    },
    {
      id: 'V-002',
      name: 'John Smith',
      age: 28,
      incident: 'Burglary',
      contact: '0987654321',
      email: 'john.smith@example.com',
      status: 'In Therapy',
      priority: 'Medium',
      lastContact: '2023-06-10'
    },
    {
      id: 'V-003',
      name: 'Alice Johnson',
      age: 42,
      incident: 'Fraud',
      contact: '0555123456',
      email: 'alice.j@example.com',
      status: 'Case Closed',
      priority: 'Low',
      lastContact: '2023-05-28'
    }
  ];

  const statusVariant = (status: string) => {
    switch (status) {
      case 'Needs Support': return 'destructive';
      case 'In Therapy': return 'warning';
      case 'Case Closed': return 'success';
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
        <span className="text-primary font-medium">Victims</span>
      </nav>

      {/* Page Header */}
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
        <div>
          <h1 className="text-2xl font-bold text-gray-800">Victim Management</h1>
          <p className="text-sm text-gray-500 mt-1">
            {role === 'investigator' 
              ? 'Your assigned victims' 
              : 'All victim records'}
          </p>
        </div>
        <div className="flex gap-3">
          <Button className="rounded-lg gap-2 border-gray-300" variant="outline">
            <PlusIcon className="h-4 w-4" />
            Add Victim
          </Button>
          <Button variant="outline" className="rounded-lg gap-2 border-gray-300">
            <FilterIcon className="h-4 w-4" />
            Filter
          </Button>
        </div>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <Card className="rounded-xl border border-gray-200">
          <CardHeader className="pb-2 px-5 pt-5">
            <CardTitle className="text-sm font-medium text-gray-600">Total Victims</CardTitle>
          </CardHeader>
          <CardContent className="px-5 pb-5">
            <div className="text-2xl font-bold text-gray-800">{victims.length}</div>
          </CardContent>
        </Card>
        <Card className="rounded-xl border border-gray-200">
          <CardHeader className="pb-2 px-5 pt-5">
            <CardTitle className="text-sm font-medium text-gray-600">Needs Support</CardTitle>
          </CardHeader>
          <CardContent className="px-5 pb-5">
            <div className="text-2xl font-bold text-gray-800">
              {victims.filter(v => v.status === 'Needs Support').length}
            </div>
          </CardContent>
        </Card>
        <Card className="rounded-xl border border-gray-200">
          <CardHeader className="pb-2 px-5 pt-5">
            <CardTitle className="text-sm font-medium text-gray-600">High Priority</CardTitle>
          </CardHeader>
          <CardContent className="px-5 pb-5">
            <div className="text-2xl font-bold text-gray-800">
              {victims.filter(v => v.priority === 'High').length}
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Victims Table */}
      <Card className="rounded-xl border border-gray-200 overflow-hidden">
        <CardHeader className="border-b p-5 bg-gray-50">
          <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
            <CardTitle className="text-lg font-semibold text-gray-800">Victim Records</CardTitle>
            <div className="relative w-full md:w-auto">
              <SearchIcon className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
              <Input
                placeholder="Search victims..."
                className="pl-9 w-full md:w-[300px] rounded-lg"
              />
            </div>
          </div>
        </CardHeader>
        <CardContent className="p-0">
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="w-[100px] px-5 py-3">ID</TableHead>
                <TableHead className="px-5 py-3">Name</TableHead>
                <TableHead className="px-5 py-3">Age</TableHead>
                <TableHead className="px-5 py-3">Incident</TableHead>
                <TableHead className="px-5 py-3">Contact</TableHead>
                <TableHead className="px-5 py-3">Status</TableHead>
                <TableHead className="px-5 py-3">Last Contact</TableHead>
                <TableHead className="text-right px-5 py-3">Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {victims.map((victim) => (
                <TableRow key={victim.id} className="hover:bg-gray-50/50">
                  <TableCell className="px-5 py-4 font-medium">{victim.id}</TableCell>
                  <TableCell className="px-5 py-4 font-medium">{victim.name}</TableCell>
                  <TableCell className="px-5 py-4">{victim.age}</TableCell>
                  <TableCell className="px-5 py-4">
                    <Badge variant={priorityVariant(victim.priority)} className="rounded-md">
                      {victim.incident}
                    </Badge>
                  </TableCell>
                  <TableCell className="px-5 py-4">
                    <div className="flex flex-col gap-1">
                      <div className="flex items-center gap-2">
                        <PhoneIcon className="h-4 w-4 text-gray-500" />
                        <span>{victim.contact}</span>
                      </div>
                      <div className="flex items-center gap-2">
                        <MailIcon className="h-4 w-4 text-gray-500" />
                        <span className="text-sm text-gray-600 truncate max-w-[160px]">
                          {victim.email}
                        </span>
                      </div>
                    </div>
                  </TableCell>
                  <TableCell className="px-5 py-4">
                    <Badge variant={statusVariant(victim.status)} className="rounded-md">
                      {victim.status}
                    </Badge>
                  </TableCell>
                  <TableCell className="px-5 py-4 text-gray-600">{victim.lastContact}</TableCell>
                  <TableCell className="px-5 py-4 text-right space-x-2">
                    <Button size="sm" variant="outline" className="rounded-lg border-gray-300">
                      View
                    </Button>
                    {(role === 'investigator' || role === 'counselor') && (
                      <Button size="sm" className="rounded-lg border-gray-300" variant="outline">
                        Support
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