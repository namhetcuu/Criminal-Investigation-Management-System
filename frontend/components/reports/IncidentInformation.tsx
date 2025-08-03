export function IncidentInformation() {
  return (
    <div className="mb-8 pb-8 border-b-2 border-gray-200">
      <h2 className="text-red-600 text-lg font-bold mb-6">
        INCIDENT INFORMATION
      </h2>

      <div className="space-y-6">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="border-b border-gray-200 pb-4">
            <h3 className="font-semibold text-gray-900 mb-2">Type of Crime</h3>
            <p className="text-gray-600">Crimes Against Property</p>
          </div>
          <div className="border-b border-gray-200 pb-4">
            <h3 className="font-semibold text-gray-900 mb-2">Severity</h3>
            <p className="text-gray-600">Moderate</p>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="border-b border-gray-200 pb-4">
            <h3 className="font-semibold text-gray-900 mb-2">
              Date/time of occurrence
            </h3>
            <p className="text-gray-600">05/20/2024 14:30</p>
          </div>
          <div className="border-b border-gray-200 pb-4">
            <h3 className="font-semibold text-gray-900 mb-2">State</h3>
            <p className="text-gray-600">Florida</p>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="border-b border-gray-200 pb-4">
            <h3 className="font-semibold text-gray-900 mb-2">
              Detailed address
            </h3>
            <div className="text-gray-600">
              <p>1234 Maplewood Avenue</p>
              <p>Florida, CA 90026</p>
              <p>United States</p>
            </div>
          </div>
          <div className="border-b border-gray-200 pb-4">
            <h3 className="font-semibold text-gray-900 mb-2">
              Description of the incident
            </h3>
            <p className="text-gray-600">
              On the evening of June 15, 2025, at approximately 7:45 PM, I
              returned to my apartment at 1234 Maplewood...
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
