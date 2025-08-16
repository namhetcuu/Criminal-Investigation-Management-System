import React from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Activity, Users, FileText, AlertCircle, ArrowUp, ArrowDown } from "lucide-react";
import { Button } from "@/components/ui/button";
import RecentCases from "./reports/components/RecentCases";
import QuickActions from "./reports/components/QuickActions";

const AdminDashboardPage = () => {
  // Mock data - replace with real data from your API
  const stats = [
    {
      title: "Total Cases",
      value: "1,234",
      change: "+12%",
      isPositive: true,
      icon: <FileText className="h-5 w-5 text-blue-500" />,
    },
    {
      title: "Active Users",
      value: "542",
      change: "+5%",
      isPositive: true,
      icon: <Users className="h-5 w-5 text-green-500" />,
    },
    {
      title: "Pending Reports",
      value: "89",
      change: "-3%",
      isPositive: false,
      icon: <AlertCircle className="h-5 w-5 text-amber-500" />,
    },
    {
      title: "Today's Activity",
      value: "47",
      change: "+8%",
      isPositive: true,
      icon: <Activity className="h-5 w-5 text-purple-500" />,
    },
  ];

  return (
    <main className="flex-1 p-4 md:p-6 bg-gray-50">
      <div className="mb-6">
        <h1 className="text-2xl md:text-3xl font-bold text-gray-800">Dashboard Overview</h1>
        <p className="text-gray-600 mt-2">
          Welcome back! Here's what's happening with your cases today.
        </p>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 mb-6">
        {stats.map((stat, index) => (
          <Card key={index} className="hover:shadow-md transition-shadow">
            <CardHeader className="flex flex-row items-center justify-between pb-2">
              <CardTitle className="text-sm font-medium text-gray-500">
                {stat.title}
              </CardTitle>
              <div className="p-2 rounded-full bg-gray-100">
                {stat.icon}
              </div>
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold text-gray-800">{stat.value}</div>
              <p
                className={`text-xs mt-1 flex items-center ${
                  stat.isPositive ? "text-green-600" : "text-red-600"
                }`}
              >
                {stat.isPositive ? (
                  <ArrowUp className="h-3 w-3 mr-1" />
                ) : (
                  <ArrowDown className="h-3 w-3 mr-1" />
                )}
                {stat.change} from yesterday
              </p>
            </CardContent>
          </Card>
        ))}
      </div>

      {/* Main Content Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Recent Cases */}
        <div className="lg:col-span-2">
          <Card className="h-full">
            <CardHeader className="border-b">
              <div className="flex justify-between items-center">
                <CardTitle className="text-lg">Recent Cases</CardTitle>
                <Button variant="ghost" size="sm" className="text-blue-600">
                  View All
                </Button>
              </div>
            </CardHeader>
            <CardContent className="p-0">
              <RecentCases />
            </CardContent>
          </Card>
        </div>

        {/* Quick Actions */}
        <div>
          <Card className="h-full">
            <CardHeader className="border-b">
              <CardTitle className="text-lg">Quick Actions</CardTitle>
            </CardHeader>
            <CardContent>
              <QuickActions />
            </CardContent>
          </Card>
        </div>
      </div>

      {/* Recent Activity */}
      <Card className="mt-6">
        <CardHeader className="border-b">
          <div className="flex justify-between items-center">
            <CardTitle className="text-lg">Recent Activity</CardTitle>
            <Button variant="ghost" size="sm" className="text-blue-600">
              View All
            </Button>
          </div>
        </CardHeader>
        <CardContent className="divide-y">
          {[1, 2, 3, 4].map((item) => (
            <div key={item} className="py-3 flex items-start gap-3">
              <div className="bg-blue-100 p-2 rounded-full">
                <Activity className="h-4 w-4 text-blue-600" />
              </div>
              <div className="flex-1">
                <p className="text-sm font-medium text-gray-800">
                  Case #{1230 + item} status updated to "In Review"
                </p>
                <p className="text-xs text-gray-500">By Officer Smith • 2 hours ago</p>
              </div>
              <Button variant="ghost" size="sm" className="text-gray-500">
                View
              </Button>
            </div>
          ))}
        </CardContent>
      </Card>
    </main>
  );
};

export default AdminDashboardPage;