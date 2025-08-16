"use client";

import {
  FiUsers,
  FiFileText,
  FiBox,
  FiActivity,
  FiTrendingUp,
  FiAlertCircle,
} from "react-icons/fi";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";

export default function AdminDashboardPage() {
  return (
    <div className="space-y-6 p-6 max-w-7xl mx-auto">
      {/* Header */}
      <div className="space-y-2">
        <h1 className="text-3xl font-bold text-gray-800">Dashboard Overview</h1>
        <p className="text-gray-500">
          Welcome back! Here's what's happening today.
        </p>
      </div>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard
          icon={<FiUsers className="h-6 w-6" />}
          label="Total Users"
          value="1,245"
          trend="12% increase"
          trendPositive={true}
        />
        <StatCard
          icon={<FiFileText className="h-6 w-6" />}
          label="New Reports"
          value="324"
          trend="5% increase"
          trendPositive={true}
        />
        <StatCard
          icon={<FiBox className="h-6 w-6" />}
          label="Active Cases"
          value="57"
          trend="3 cases today"
        />
        <StatCard
          icon={<FiActivity className="h-6 w-6" />}
          label="Pending Actions"
          value="12"
          trend="2 urgent"
          alert={true}
        />
      </div>

      {/* Charts Section */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-4">
        <Card className="lg:col-span-2">
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <FiTrendingUp className="h-5 w-5 text-blue-600" />
              Monthly Activity
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="flex items-center justify-center h-64 text-gray-400 border border-dashed border-gray-300 rounded-lg">
              <div className="text-center p-4">
                <p>Activity chart will appear here</p>
                <p className="text-sm text-gray-500 mt-1">
                  Visualizing report trends over time
                </p>
              </div>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <FiAlertCircle className="h-5 w-5 text-red-600" />
              Priority Alerts
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              <div className="p-3 bg-red-50 rounded-lg border-l-4 border-red-500">
                <p className="font-medium">Case #1024 - Assault</p>
                <p className="text-sm text-gray-600 mt-1">
                  Requires immediate attention
                </p>
              </div>
              <div className="p-3 bg-yellow-50 rounded-lg border-l-4 border-yellow-500">
                <p className="font-medium">Report #2048 - Burglary</p>
                <p className="text-sm text-gray-600 mt-1">
                  Pending supervisor review
                </p>
              </div>
              <div className="p-3 bg-blue-50 rounded-lg border-l-4 border-blue-500">
                <p className="font-medium">User Verification</p>
                <p className="text-sm text-gray-600 mt-1">
                  15 new users to verify
                </p>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Recent Reports Table */}
      <Card>
        <CardHeader>
          <CardTitle>Recent Reports</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="w-[100px]">Report ID</TableHead>
                <TableHead>Title</TableHead>
                <TableHead className="w-[150px]">Type</TableHead>
                <TableHead className="w-[120px]">Status</TableHead>
                <TableHead className="text-right w-[120px]">Date</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              <TableRow className="hover:bg-gray-50/50">
                <TableCell className="font-medium">#1001</TableCell>
                <TableCell className="max-w-[300px] truncate">
                  Burglary in Downtown
                </TableCell>
                <TableCell>
                  <Badge variant="outline">Property Crime</Badge>
                </TableCell>
                <TableCell>
                  <Badge variant="success">Resolved</Badge>
                </TableCell>
                <TableCell className="text-right">2025-08-01</TableCell>
              </TableRow>
              <TableRow className="hover:bg-gray-50/50">
                <TableCell className="font-medium">#1002</TableCell>
                <TableCell className="max-w-[300px] truncate">
                  Missing Person
                </TableCell>
                <TableCell>
                  <Badge variant="outline">Person Crime</Badge>
                </TableCell>
                <TableCell>
                  <Badge variant="warning">In Progress</Badge>
                </TableCell>
                <TableCell className="text-right">2025-08-05</TableCell>
              </TableRow>
              <TableRow className="hover:bg-gray-50/50">
                <TableCell className="font-medium">#1003</TableCell>
                <TableCell className="max-w-[300px] truncate">
                  Vehicle Theft
                </TableCell>
                <TableCell>
                  <Badge variant="outline">Property Crime</Badge>
                </TableCell>
                <TableCell>
                  <Badge variant="destructive">Pending</Badge>
                </TableCell>
                <TableCell className="text-right">2025-08-08</TableCell>
              </TableRow>
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </div>
  );
}

function StatCard({
  icon,
  label,
  value,
  trend,
  trendPositive,
  alert,
}: {
  icon: React.ReactNode;
  label: string;
  value: string;
  trend?: string;
  trendPositive?: boolean;
  alert?: boolean;
}) {
  return (
    <Card className="hover:shadow-md transition-shadow">
      <CardContent className="p-6 flex items-center">
        <div
          className={`p-3 rounded-full ${alert ? "bg-red-100 text-red-600" : "bg-blue-100 text-blue-600"}`}
        >
          {icon}
        </div>
        <div className="ml-4">
          <p className="text-sm text-gray-500">{label}</p>
          <p className="text-2xl font-bold mt-1">{value}</p>
          {trend && (
            <p
              className={`text-xs mt-1 ${trendPositive ? "text-green-600" : alert ? "text-red-600" : "text-gray-500"}`}
            >
              {trend}
            </p>
          )}
        </div>
      </CardContent>
    </Card>
  );
}
