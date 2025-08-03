import { Mail, MapPin, Phone } from "lucide-react";

export function MyInformation() {
  return (
    <div className="mb-8 pb-8 border-b-2 border-gray-200">
      <h2 className="text-red-600 text-lg font-bold mb-6">MY INFORMATION</h2>

      <div className="space-y-6">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="border-b border-gray-200 pb-4">
            <h3 className="font-semibold text-gray-900 mb-2">Full name</h3>
            <p className="text-gray-600">Nguyen Van A</p>
          </div>
          <div className="border-b border-gray-200 pb-4">
            <h3 className="font-semibold text-gray-900 mb-2">Email</h3>
            <div className="flex items-center gap-2">
              <Mail className="w-4 h-4 text-gray-400" />
              <p className="text-blue-600">abcd@gmail.com</p>
            </div>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="border-b border-gray-200 pb-4">
            <h3 className="font-semibold text-gray-900 mb-2">
              Relationship to the incident
            </h3>
            <p className="text-gray-600">Witness</p>
          </div>
          <div className="border-b border-gray-200 pb-4">
            <h3 className="font-semibold text-gray-900 mb-2">Phone</h3>
            <div className="flex items-center gap-2">
              <Phone className="w-4 h-4 text-gray-400" />
              <p className="text-gray-600">+1 (555) 123-4567</p>
            </div>
          </div>
        </div>

        <div className="border-b border-gray-200 pb-4">
          <h3 className="font-semibold text-gray-900 mb-2">Address</h3>
          <div className="flex items-start gap-2">
            <MapPin className="w-4 h-4 text-gray-400 mt-1" />
            <div className="text-gray-600">
              <p>1234 Maplewood Avenue</p>
              <p>Florida, CA 90026</p>
              <p>United States</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
